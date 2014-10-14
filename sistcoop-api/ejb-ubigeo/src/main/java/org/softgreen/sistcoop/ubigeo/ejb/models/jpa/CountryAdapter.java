package org.softgreen.sistcoop.ubigeo.ejb.models.jpa;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.ubigeo.client.models.CountryModel;
import org.softgreen.sistcoop.ubigeo.client.models.CurrencyModel;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.CountryEntity;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.CurrencyEntity;

public class CountryAdapter implements CountryModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected CountryEntity countryEntity;
	protected EntityManager em;

	public CountryAdapter(EntityManager em, CountryEntity tipoDocumentoEntity) {
		this.em = em;
		this.countryEntity = tipoDocumentoEntity;
	}

	public CountryEntity getCountryEntity() {
		return countryEntity;
	}

	@Override
	public Integer getId() {
		return countryEntity.getId();
	}

	@Override
	public String getAlpha2Code() {
		return countryEntity.getAlpha2Code();
	}

	@Override
	public void setAlpha2Code(String alpha2Code) {
		countryEntity.setAlpha2Code(alpha2Code);
	}

	@Override
	public String getShortName() {
		return countryEntity.getShortName();
	}

	@Override
	public void setShortName(String shortName) {
		countryEntity.setShortName(shortName);
	}

	@Override
	public String getShortNameLowerCase() {
		return countryEntity.getShortNameLowerCase();
	}

	@Override
	public void setShortNameLowerCase(String shortNameLowerCase) {
		countryEntity.setShortNameLowerCase(shortNameLowerCase);
	}

	@Override
	public String getFullName() {
		return countryEntity.getFullName();
	}

	@Override
	public void setFullName(String fullName) {
		countryEntity.setFullName(fullName);
	}

	@Override
	public String getAlpha3Code() {
		return countryEntity.getAlpha3Code();
	}

	@Override
	public void setAlpha3Code(String alpha3Code) {
		countryEntity.setAlpha3Code(alpha3Code);
	}

	@Override
	public String getNumericCode() {
		return countryEntity.getNumericCode();
	}

	@Override
	public void setNumericCode(String numericCode) {
		countryEntity.setNumericCode(numericCode);
	}

	@Override
	public String getRemarks() {
		return countryEntity.getRemarks();
	}

	@Override
	public void setRemarks(String remarks) {
		countryEntity.setRemarks(remarks);
	}

	@Override
	public boolean isIndependent() {
		return countryEntity.isIndependent();
	}

	@Override
	public void setIndependent(boolean independent) {
		countryEntity.setIndependent(independent);
	}

	@Override
	public String getTerritoryName() {
		return countryEntity.getTerritoryName();
	}

	@Override
	public void setTerritoryName(String territoryName) {
		countryEntity.setTerritoryName(territoryName);
	}

	@Override
	public String getStatus() {
		return countryEntity.getStatus();
	}

	@Override
	public void setStatus(String status) {
		countryEntity.setStatus(status);
	}

	@Override
	public CurrencyModel getCurrency() {
		return null;
	}

	@Override
	public void setCurrency(CurrencyModel currency) {
		CurrencyEntity currencyEntity = CurrencyAdapter.toCurrencyEntity(currency, em);
		countryEntity.setCurrency(currencyEntity);
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub

	}

	public static CountryEntity toCountryEntity(CountryModel model, EntityManager em) {
		if (model instanceof CountryAdapter) {
			return ((CountryAdapter) model).getCountryEntity();
		}
		return em.getReference(CountryEntity.class, model.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof CountryModel))
			return false;

		CountryModel that = (CountryModel) o;
		return that.getId().equals(getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}
}
