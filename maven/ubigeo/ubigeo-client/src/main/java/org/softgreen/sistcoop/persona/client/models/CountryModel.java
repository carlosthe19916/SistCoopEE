package org.softgreen.sistcoop.persona.client.models;

public interface CountryModel extends Model {

	public Integer getId();

	public String getAlpha2Code();

	public void setAlpha2Code(String alpha2Code);

	public String getShortName();

	public void setShortName(String shortName);

	public String getShortNameLowerCase();

	public void setShortNameLowerCase(String shortNameLowerCase);

	public String getFullName();

	public void setFullName(String fullName);

	public String getAlpha3Code();

	public void setAlpha3Code(String alpha3Code);

	public String getNumericCode();

	public void setNumericCode(String numericCode);

	public String getRemarks();

	public void setRemarks(String remarks);

	public boolean isIndependent();

	public void setIndependent(boolean independent);

	public String getTerritoryName();

	public void setTerritoryName(String territoryName);

	public String getStatus();

	public void setStatus(String status);

	public CurrencyModel getCurrency();

	public void setCurrency(CurrencyModel currency);

}