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

import org.softgreen.sistcoop.organizacion.client.models.SucursalModel;
import org.softgreen.sistcoop.organizacion.client.models.SucursalProvider;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.SucursalEntity;

@Named
@Stateless
@Local(SucursalProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaSucursalProvider implements SucursalProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public SucursalModel addSucursal(String abreviatura, String denominacion) {
		SucursalEntity sucursalEntity = new SucursalEntity();
		sucursalEntity.setAbreviatura(abreviatura);
		sucursalEntity.setDenominacion(denominacion);
		sucursalEntity.setEstado(true);
		em.persist(sucursalEntity);
		return new SucursalAdapter(em, sucursalEntity);
	}

	@Override
	public boolean removeSucursal(SucursalModel sucursalModel) {
		SucursalEntity sucursalEntity = em.find(SucursalEntity.class, sucursalModel.getId());
		if (em.contains(sucursalEntity))
			em.remove(sucursalEntity);
		else
			em.remove(em.getReference(SucursalEntity.class, sucursalEntity.getId()));
		return true;
	}

	@Override
	public SucursalModel getSucursalById(Integer id) {
		SucursalEntity sucursalEntity = this.em.find(SucursalEntity.class, id);
		return sucursalEntity != null ? new SucursalAdapter(em, sucursalEntity) : null;
	}

	@Override
	public List<SucursalModel> getSucursales() {
		return getSucursales(true);
	}

	@Override
	public List<SucursalModel> getSucursales(boolean estado) {
		TypedQuery<SucursalEntity> query = em.createNamedQuery(SucursalEntity.findByEstado, SucursalEntity.class);
		query.setParameter("estado", estado);
		List<SucursalEntity> list = query.getResultList();
		List<SucursalModel> results = new ArrayList<SucursalModel>();
		for (SucursalEntity entity : list) {
			results.add(new SucursalAdapter(em, entity));
		}
		return results;
	}

	@Override
	public List<SucursalModel> getSucursales(String filterText, int limit, int offset) {
		TypedQuery<SucursalEntity> query = em.createNamedQuery(SucursalEntity.findByFilterText, SucursalEntity.class);
		if (filterText == null)
			filterText = "";
		if (limit != -1)
			query.setFirstResult(offset);
		if (offset != -1)
			query.setMaxResults(offset);
		query.setParameter("filterText", "%" + filterText + "%");
		List<SucursalEntity> list = query.getResultList();
		List<SucursalModel> results = new ArrayList<SucursalModel>();
		for (SucursalEntity entity : list) {
			results.add(new SucursalAdapter(em, entity));
		}
		return results;
	}

}
