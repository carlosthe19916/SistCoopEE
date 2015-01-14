package org.softgreen.sistcoop.ubigeo.client.models;

import java.util.Set;

public interface CurrencyModel extends Model {

	String getCode();

	void setCode(String code);

	String getDenomination();

	void setDenomination(String denomination);

	String getSimbol();

	void setSimbol(String simbol);

	Set<DenominationModel> getDenominations();

	void setDenominations(Set<DenominationModel> denominations);

}