package org.softgreen.sistcoop.ubigeo.ejb.models.jpa;

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

import org.softgreen.sistcoop.ubigeo.client.models.DepartamentoModel;
import org.softgreen.sistcoop.ubigeo.client.models.DistritoModel;
import org.softgreen.sistcoop.ubigeo.client.models.ProvinciaModel;
import org.softgreen.sistcoop.ubigeo.client.models.UbigeoProvider;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.DepartamentoEntity;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.DistritoEntity;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.ProvinciaEntity;

@Named
@Stateless
@Local(UbigeoProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaUbigeoProvider implements UbigeoProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public List<DepartamentoModel> getDepartamentos() {
		TypedQuery<DepartamentoEntity> query = em.createNamedQuery(DepartamentoEntity.findAll, DepartamentoEntity.class);
		List<DepartamentoEntity> list = query.getResultList();
		List<DepartamentoModel> results = new ArrayList<DepartamentoModel>();
		for (DepartamentoEntity entity : list) {
			results.add(new DepartamentoAdapter(em, entity));
		}
		return results;
	}

	@Override
	public List<ProvinciaModel> getProvincias(String codigoDepartamento) {
		TypedQuery<ProvinciaEntity> query = em.createNamedQuery(ProvinciaEntity.findByCodDepartamento, ProvinciaEntity.class);
		query.setParameter("codigoDepartamento", codigoDepartamento);
		List<ProvinciaEntity> list = query.getResultList();
		List<ProvinciaModel> results = new ArrayList<ProvinciaModel>();
		for (ProvinciaEntity entity : list) {
			results.add(new ProvinciaAdapter(em, entity));
		}
		return results;
	}

	@Override
	public List<DistritoModel> getDistritos(String codigoDepartamento, String codigoProvincia) {
		TypedQuery<DistritoEntity> query = em.createNamedQuery(DistritoEntity.findByCodDepartamentoProvincia, DistritoEntity.class);
		query.setParameter("codigoDepartamento", codigoDepartamento);
		query.setParameter("codigoProvincia", codigoProvincia);
		List<DistritoEntity> list = query.getResultList();
		List<DistritoModel> results = new ArrayList<DistritoModel>();
		for (DistritoEntity entity : list) {
			results.add(new DistritoAdapter(em, entity));
		}
		return results;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}
	
}
