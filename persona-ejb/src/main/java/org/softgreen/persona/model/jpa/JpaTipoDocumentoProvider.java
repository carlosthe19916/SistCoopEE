package org.softgreen.persona.model.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.softgreen.persona.model.TipoDocumentoModel;
import org.softgreen.persona.model.jpa.entity.TipoDocumentoEntity;
import org.softgreen.persona.model.type.TipoPersona;
import org.softgreen.persona.provider.TipoDocumentoProvider;

@Named
@Stateless
@Remote(TipoDocumentoProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaTipoDocumentoProvider implements TipoDocumentoProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public TipoDocumentoModel getTipoDocumentoByAbreviatura(String abreviatura) {
		TypedQuery<TipoDocumentoEntity> query = em.createNamedQuery(TipoDocumentoEntity.findByAbreviatura, TipoDocumentoEntity.class);
		query.setParameter("abreviatura", abreviatura);
		List<TipoDocumentoEntity> results = query.getResultList();
		if (results.size() == 0)
			return null;
		return new TipoDocumentoAdapter(em, results.get(0));
	}

	@Override
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
	public TipoDocumentoModel addTipoDocumento(String abreviatura, String denominacion, int maxLength, TipoPersona tipoPersona) {
		TipoDocumentoEntity tipoDocumentoEntity = new TipoDocumentoEntity();
		tipoDocumentoEntity.setAbreviatura(abreviatura);
		tipoDocumentoEntity.setDenominacion(denominacion);
		tipoDocumentoEntity.setCantidadCaracteres(maxLength);
		tipoDocumentoEntity.setTipoPersona(tipoPersona);
		em.persist(tipoDocumentoEntity);
		return new TipoDocumentoAdapter(em, tipoDocumentoEntity);
	}

	@Override
	public boolean removeTipoDocumento(TipoDocumentoModel tipoDocumentoModel) {
		TipoDocumentoEntity tipoDocumentoEntity = TipoDocumentoAdapter.toTipoDocumentoEntity(tipoDocumentoModel, em);
		em.remove(tipoDocumentoEntity);
		return true;
	}

}