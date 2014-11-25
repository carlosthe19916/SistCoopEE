package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.AgenciaProvider;
import org.softgreen.sistcoop.organizacion.client.models.SucursalModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.AgenciaEntity;

@Named
@Stateless
@Local(AgenciaProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaAgenciaProvider implements AgenciaProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean removeAgencia(AgenciaModel AgenciaModel) {
		AgenciaEntity AgenciaEntity = em.find(AgenciaEntity.class, AgenciaModel.getId());
		if (em.contains(AgenciaEntity))
			em.remove(AgenciaEntity);
		else
			em.remove(em.getReference(AgenciaEntity.class, AgenciaEntity.getId()));
		return true;
	}

	@Override
	public AgenciaModel getAgenciaById(Integer id) {
		AgenciaEntity AgenciaEntity = this.em.find(AgenciaEntity.class, id);
		return AgenciaEntity != null ? new AgenciaAdapter(em, AgenciaEntity) : null;
	}

	@Override
	public AgenciaModel addAgencia(SucursalModel sucursal, String codigo,
			String abreviatura, String denominacion, String ubigeo) {
		AgenciaEntity agenciaEntity = new AgenciaEntity();
		agenciaEntity.setCodigo(codigo);
		agenciaEntity.setAbreviatura(abreviatura);
		agenciaEntity.setDenominacion(denominacion);
		agenciaEntity.setUbigeo(ubigeo);
		agenciaEntity.setEstado(true);
		em.persist(agenciaEntity);
		return new AgenciaAdapter(em, agenciaEntity);
	}

	@Override
	public AgenciaModel getAgenciaByCodigo(String codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AgenciaModel> getAgencias() {
		return getAgencias(true);
	}

	@Override
	public List<AgenciaModel> getAgencias(boolean estado) {
		TypedQuery<AgenciaEntity> query = em.createNamedQuery(AgenciaEntity.findByEstado, AgenciaEntity.class);
		query.setParameter("estado", estado);
		List<AgenciaEntity> list = query.getResultList();
		List<AgenciaModel> results = new ArrayList<AgenciaModel>();
		for (AgenciaEntity entity : list) {
			results.add(new AgenciaAdapter(em, entity));
		}
		return results;
	}

}
