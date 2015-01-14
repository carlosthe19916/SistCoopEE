package org.softgreen.sistcoop.ubigeo.client.models;

public interface CountryModel extends Model {

	Integer getId();

	String getAlpha2Code();

	void setAlpha2Code(String alpha2Code);

	String getShortName();

	void setShortName(String shortName);

	String getShortNameLowerCase();

	void setShortNameLowerCase(String shortNameLowerCase);

	String getFullName();

	void setFullName(String fullName);

	String getAlpha3Code();

	void setAlpha3Code(String alpha3Code);

	String getNumericCode();

	void setNumericCode(String numericCode);

	String getRemarks();

	void setRemarks(String remarks);

	boolean isIndependent();

	void setIndependent(boolean independent);

	String getTerritoryName();

	void setTerritoryName(String territoryName);

	String getStatus();

	void setStatus(String status);

	CurrencyModel getCurrency();

	void setCurrency(CurrencyModel currency);

}