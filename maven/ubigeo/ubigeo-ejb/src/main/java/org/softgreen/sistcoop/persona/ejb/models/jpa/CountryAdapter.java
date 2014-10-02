package org.softgreen.sistcoop.persona.ejb.models.jpa;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.persona.client.models.CountryModel;
import org.softgreen.sistcoop.persona.client.models.CurrencyModel;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.CountryEntity;

public class CountryAdapter implements CountryModel {

	protected CountryEntity tipoDocumentoEntity;
	protected EntityManager em;

	public CountryAdapter(EntityManager em, CountryEntity tipoDocumentoEntity) {
		this.em = em;
		this.tipoDocumentoEntity = tipoDocumentoEntity;
	}

	@Override
	public Integer getId() {
		return tipoDocumentoEntity.getId();
	}

	@Override
	public String getAlpha2Code() {
		return tipoDocumentoEntity.getAlpha2Code();
	}

	@Override
	public void setAlpha2Code(String alpha2Code) {
		tipoDocumentoEntity.setAlpha2Code(alpha2Code);
	}

	@Override
	public String getShortName() {
		return tipoDocumentoEntity.getShortName();
	}

	@Override
	public void setShortName(String shortName) {
		tipoDocumentoEntity.setShortName(shortName);
	}

	@Override
	public String getShortNameLowerCase() {
		return tipoDocumentoEntity.getShortNameLowerCase();
	}

	@Override
	public void setShortNameLowerCase(String shortNameLowerCase) {
		tipoDocumentoEntity.setShortNameLowerCase(shortNameLowerCase);
	}

	@Override
	public String getFullName() {
		return tipoDocumentoEntity.getFullName();
	}

	@Override
	public void setFullName(String fullName) {
		tipoDocumentoEntity.setFullName(fullName);
	}

	@Override
	public String getAlpha3Code() {
		return tipoDocumentoEntity.getAlpha3Code();
	}

	@Override
	public void setAlpha3Code(String alpha3Code) {
		tipoDocumentoEntity.setAlpha3Code(alpha3Code);
	}

	@Override
	public String getNumericCode() {
		return tipoDocumentoEntity.getNumericCode();
	}

	@Override
	public void setNumericCode(String numericCode) {
		tipoDocumentoEntity.setNumericCode(numericCode);
	}

	@Override
	public String getRemarks() {
return tipoDocumentoEntity.getRemarks();
	}

	@Override
	public void setRemarks(String remarks) {
		tipoDocumentoEntity.setRemarks(remarks);
	}

	@Override
	public boolean isIndependent() {
		return tipoDocumentoEntity.isIndependent();
	}

	@Override
	public void setIndependent(boolean independent) {
		tipoDocumentoEntity.setIndependent(independent);
	}

	@Override
	public String getTerritoryName() {
		return tipoDocumentoEntity.getTerritoryName();
	}

	@Override
	public void setTerritoryName(String territoryName) {
		tipoDocumentoEntity.setTerritoryName(territoryName);
	}

	@Override
	public String getStatus() {
		return tipoDocumentoEntity.getStatus();
	}

	@Override
	public void setStatus(String status) {
		tipoDocumentoEntity.setStatus(status);
	}

	@Override
	public CurrencyModel getCurrency() {
		return null;
	}

	@Override
	public void setCurrency(CurrencyModel currency) {
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
