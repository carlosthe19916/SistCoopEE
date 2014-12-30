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

import org.softgreen.sistcoop.ubigeo.client.models.CurrencyModel;
import org.softgreen.sistcoop.ubigeo.client.models.CurrencyProvider;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.CurrencyEntity;

@Named
@Stateless
@Local(CurrencyProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaCurrencyProvider implements CurrencyProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {

	}

	@Override
	public CurrencyModel findByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CurrencyModel findBySimbol(String simbol) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CurrencyModel> findAll() {
		TypedQuery<CurrencyEntity> query = em.createNamedQuery(CurrencyEntity.findAll, CurrencyEntity.class);
		List<CurrencyEntity> list = query.getResultList();
		List<CurrencyModel> results = new ArrayList<CurrencyModel>();
		for (CurrencyEntity entity : list) {
			results.add(new CurrencyAdapter(em, entity));
		}
		return results;
	}

}
