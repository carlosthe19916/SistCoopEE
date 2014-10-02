package org.softgreen.sistcoop.persona.ejb.models.jpa;

import java.util.Set;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.persona.client.models.CountryModel;
import org.softgreen.sistcoop.persona.client.models.CurrencyModel;
import org.softgreen.sistcoop.persona.client.models.DenominationModel;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.CountryEntity;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.CurrencyEntity;

public class CurrencyAdapter implements CurrencyModel {

	protected CurrencyEntity currencyEntity;
	protected EntityManager em;

	public CurrencyAdapter(EntityManager em, CurrencyEntity currencyEntity) {
		this.em = em;
		this.currencyEntity = currencyEntity;
	}

	@Override
	public String getCode() {
		return currencyEntity.getCode();
	}

	@Override
	public void setCode(String code) {
		currencyEntity.setCode(code);
	}

	@Override
	public String getDenomination() {
		return currencyEntity.getDenomination();
	}

	@Override
	public void setDenomination(String denomination) {
		currencyEntity.setDenomination(denomination);
	}

	@Override
	public String getSimbol() {
		return currencyEntity.getSimbol();
	}

	@Override
	public void setSimbol(String simbol) {
		currencyEntity.setSimbol(simbol);
	}

	@Override
	public Set<DenominationModel> getDenominations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDenominations(Set<DenominationModel> denominations) {
		// TODO Auto-generated method stub

	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof CurrencyModel))
			return false;

		CurrencyModel that = (CurrencyModel) o;
		return that.getCode().equals(getCode());
	}

	@Override
	public int hashCode() {
		return getCode().hashCode();
	}
}
