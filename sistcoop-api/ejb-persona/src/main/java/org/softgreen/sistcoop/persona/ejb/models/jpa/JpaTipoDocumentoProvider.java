package org.softgreen.sistcoop.persona.ejb.models.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.softgreen.sistcoop.persona.clien.enums.TipoPersona;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.client.util.Roles;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.TipoDocumentoEntity;

@Named
@Stateless
@DeclareRoles({ Roles.ADMIN, Roles.USER })
@Local(TipoDocumentoProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaTipoDocumentoProvider implements TipoDocumentoProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	@RolesAllowed({ Roles.ADMIN })
	public TipoDocumentoModel addTipoDocumento(String abreviatura, String denominacion, int cantidadCaracteres, TipoPersona tipoPersona) {
		TipoDocumentoEntity tipoDocumentoEntity = new TipoDocumentoEntity();
		tipoDocumentoEntity.setAbreviatura(abreviatura);
		tipoDocumentoEntity.setDenominacion(denominacion);
		tipoDocumentoEntity.setCantidadCaracteres(cantidadCaracteres);
		tipoDocumentoEntity.setTipoPersona(tipoPersona);
		em.persist(tipoDocumentoEntity);
		return new TipoDocumentoAdapter(em, tipoDocumentoEntity);
	}

	@Override
	@PermitAll
	public TipoDocumentoModel getTipoDocumentoByAbreviatura(String abreviatura) {
		TypedQuery<TipoDocumentoEntity> query = em.createNamedQuery(TipoDocumentoEntity.findByAbreviatura, TipoDocumentoEntity.class);
		query.setParameter("abreviatura", abreviatura);
		List<TipoDocumentoEntity> results = query.getResultList();
		if (results.size() == 0)
			return null;
		return new TipoDocumentoAdapter(em, results.get(0));
	}

	@Override
	@PermitAll
	public List<TipoDocumentoModel> getTiposDocumento() {
		TypedQuery<TipoDocumentoEntity> query = em.createNamedQuery(TipoDocumentoEntity.findAll, TipoDocumentoEntity.class);
		List<TipoDocumentoEntity> list = query.getResultList();
		List<TipoDocumentoModel> results = new ArrayList<TipoDocumentoModel>();
		for (TipoDocumentoEntity entity : list) {
			results.add(new TipoDocumentoAdapter(em, entity));
		}
		return results;
	}

	@Override
	@PermitAll
	public List<TipoDocumentoModel> getTiposDocumento(TipoPersona tipoPersona) {
		TypedQuery<TipoDocumentoEntity> query = em.createNamedQuery(TipoDocumentoEntity.findByTipopersona, TipoDocumentoEntity.class);
		query.setParameter("tipoPersona", tipoPersona);
		List<TipoDocumentoEntity> list = query.getResultList();
		List<TipoDocumentoModel> results = new ArrayList<TipoDocumentoModel>();
		for (TipoDocumentoEntity entity : list) {
			results.add(new TipoDocumentoAdapter(em, entity));
		}
		return results;
	}

	@Override
	@RolesAllowed({ Roles.ADMIN })
	public boolean removeTipoDocumento(TipoDocumentoModel tipoDocumentoModel) {
		TipoDocumentoEntity tipoDocumentoEntity = TipoDocumentoAdapter.toTipoDocumentoEntity(tipoDocumentoModel, em);
		if (em.contains(tipoDocumentoEntity))
			em.remove(tipoDocumentoEntity);
		else
			em.remove(em.getReference(TipoDocumentoEntity.class, tipoDocumentoEntity.getAbreviatura()));
		return true;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

}
