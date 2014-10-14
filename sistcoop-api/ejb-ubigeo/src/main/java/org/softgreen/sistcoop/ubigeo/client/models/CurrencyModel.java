package org.softgreen.sistcoop.ubigeo.client.models;

import java.util.Set;

public interface CurrencyModel extends Model {

	public String getCode();

	public void setCode(String code);

	public String getDenomination();

	public void setDenomination(String denomination);

	public String getSimbol();

	public void setSimbol(String simbol);

	public Set<DenominationModel> getDenominations();

	public void setDenominations(Set<DenominationModel> denominations);

}