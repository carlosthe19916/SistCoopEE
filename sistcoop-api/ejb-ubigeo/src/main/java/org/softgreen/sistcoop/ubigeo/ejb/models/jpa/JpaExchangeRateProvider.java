package org.softgreen.sistcoop.ubigeo.ejb.models.jpa;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.softgreen.sistcoop.ubigeo.client.models.ExchangeRateProvider;

@Named
@Stateless
@Local(ExchangeRateProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaExchangeRateProvider implements ExchangeRateProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {

	}

}
