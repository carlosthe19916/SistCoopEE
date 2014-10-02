package org.softgreen.sistcoop.persona.ejb.models.jpa;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.persona.client.models.CurrencyModel;
import org.softgreen.sistcoop.persona.client.models.DenominationModel;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.DenominationEntity;

public class DenominationAdapter implements DenominationModel {

	protected DenominationEntity denominationEntity;
	protected EntityManager em;

	public DenominationAdapter(EntityManager em, DenominationEntity denominationEntity) {
		this.em = em;
		this.denominationEntity = denominationEntity;
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
		denominationEntity.setCurrency(currency);
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
