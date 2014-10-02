package org.softgreen.sistcoop.ubigeo.ejb.models.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.ubigeo.client.models.CurrencyModel;
import org.softgreen.sistcoop.ubigeo.client.models.DenominationModel;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.CurrencyEntity;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.DenominationEntity;

public class CurrencyAdapter implements CurrencyModel {

	protected CurrencyEntity currencyEntity;
	protected EntityManager em;

	public CurrencyAdapter(EntityManager em, CurrencyEntity currencyEntity) {
		this.em = em;
		this.currencyEntity = currencyEntity;
	}

	public CurrencyEntity getCurrencyEntity() {
		return currencyEntity;
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
		Set<DenominationEntity> denominations = currencyEntity.getDenominations();
		Set<DenominationModel> result = new HashSet<DenominationModel>();
		for (DenominationEntity entity : denominations) {
			result.add(new DenominationAdapter(em, entity));
		}
		return result;
	}

	@Override
	public void setDenominations(Set<DenominationModel> denominations) {
		Set<DenominationEntity> result = new HashSet<DenominationEntity>();
		for (DenominationModel model : denominations) {
			DenominationEntity entity = DenominationAdapter.toDenominationEntity(model, em);
			result.add(entity);
		}
		currencyEntity.setDenominations(result);
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub

	}

	public static CurrencyEntity toCurrencyEntity(CurrencyModel model, EntityManager em) {
		if (model instanceof CurrencyAdapter) {
			return ((CurrencyAdapter) model).getCurrencyEntity();
		}
		return em.getReference(CurrencyEntity.class, model.getCode());
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
