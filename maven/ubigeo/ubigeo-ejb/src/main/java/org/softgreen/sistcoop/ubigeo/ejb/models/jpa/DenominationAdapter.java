package org.softgreen.sistcoop.ubigeo.ejb.models.jpa;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.ubigeo.client.models.CurrencyModel;
import org.softgreen.sistcoop.ubigeo.client.models.DenominationModel;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.CurrencyEntity;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.DenominationEntity;

public class DenominationAdapter implements DenominationModel {

	protected DenominationEntity denominationEntity;
	protected EntityManager em;

	public DenominationAdapter(EntityManager em, DenominationEntity denominationEntity) {
		this.em = em;
		this.denominationEntity = denominationEntity;
	}

	public DenominationEntity getDenominationEntity() {
		return denominationEntity;
	}

	@Override
	public Integer getId() {
		return denominationEntity.getId();
	}

	@Override
	public CurrencyModel getCurrency() {
		return new CurrencyAdapter(em, denominationEntity.getCurrency());
	}

	@Override
	public void setCurrency(CurrencyModel currency) {
		CurrencyEntity currencyEntity = CurrencyAdapter.toCurrencyEntity(currency, em);
		denominationEntity.setCurrency(currencyEntity);
	}

	@Override
	public BigDecimal getValue() {
		return denominationEntity.getValue();
	}

	@Override
	public void setValue(BigDecimal value) {
		denominationEntity.setValue(value);
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub

	}

	public static DenominationEntity toDenominationEntity(DenominationModel model, EntityManager em) {
		if (model instanceof DenominationAdapter) {
			return ((DenominationAdapter) model).getDenominationEntity();
		}
		return em.getReference(DenominationEntity.class, model.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof DenominationModel))
			return false;

		DenominationModel that = (DenominationModel) o;
		return that.getId().equals(getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}
}
