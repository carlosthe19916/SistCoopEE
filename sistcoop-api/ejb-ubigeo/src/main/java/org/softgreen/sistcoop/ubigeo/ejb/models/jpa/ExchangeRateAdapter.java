package org.softgreen.sistcoop.ubigeo.ejb.models.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.ubigeo.client.models.CurrencyModel;
import org.softgreen.sistcoop.ubigeo.client.models.ExchangeRateModel;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.CurrencyEntity;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.ExchangeRateEntity;

public class ExchangeRateAdapter implements ExchangeRateModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ExchangeRateEntity exchangeRateEntity;
	protected EntityManager em;

	public ExchangeRateAdapter(EntityManager em, ExchangeRateEntity exchangeRateEntity) {
		this.em = em;
		this.exchangeRateEntity = exchangeRateEntity;
	}

	public ExchangeRateEntity getExchangeRateEntity() {
		return exchangeRateEntity;
	}

	@Override
	public void commit() {
		em.merge(exchangeRateEntity);
	}

	public static ExchangeRateEntity toExchangeRateEntity(ExchangeRateModel model, EntityManager em) {
		if (model instanceof ExchangeRateAdapter) {
			return ((ExchangeRateAdapter) model).getExchangeRateEntity();
		}
		return em.getReference(ExchangeRateEntity.class, model.getId());
	}

	@Override
	public Long getId() {
		return exchangeRateEntity.getId();
	}

	@Override
	public CurrencyModel getCurrencyOrigin() {
		return new CurrencyAdapter(em, exchangeRateEntity.getCurrencyOrigin());
	}

	@Override
	public void setCurrencyOrigin(CurrencyModel currencyModel) {
		CurrencyEntity currencyEntity = CurrencyAdapter.toCurrencyEntity(currencyModel, em);
		exchangeRateEntity.setCurrencyOrigin(currencyEntity);
	}

	@Override
	public CurrencyModel getCurrencyDestiny() {
		return new CurrencyAdapter(em, exchangeRateEntity.getCurrencyDestiny());
	}

	@Override
	public void setCurrencyDestiny(CurrencyModel currencyModel) {
		CurrencyEntity currencyEntity = CurrencyAdapter.toCurrencyEntity(currencyModel, em);
		exchangeRateEntity.setCurrencyDestiny(currencyEntity);
	}

	@Override
	public Date getFecha() {
		return exchangeRateEntity.getFecha();
	}

	@Override
	public void setFecha(Date fecha) {
		exchangeRateEntity.setFecha(fecha);
		;
	}

	@Override
	public BigDecimal getValor() {
		return exchangeRateEntity.getValor();
	}

	@Override
	public void setValor(BigDecimal valor) {
		exchangeRateEntity.setValor(valor);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof ExchangeRateModel))
			return false;

		ExchangeRateModel that = (ExchangeRateModel) o;
		return that.getId().equals(getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

}
