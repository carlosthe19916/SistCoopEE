package org.softgreen.sistcoop.ubigeo.client.models;

import java.math.BigDecimal;

public interface DenominationModel extends Model {

	Integer getId();

	CurrencyModel getCurrency();

	void setCurrency(CurrencyModel currency);

	BigDecimal getValue();

	void setValue(BigDecimal value);

}