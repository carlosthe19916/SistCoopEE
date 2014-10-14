package org.softgreen.sistcoop.ubigeo.client.models;

import java.math.BigDecimal;

public interface DenominationModel extends Model {

	public Integer getId();

	public CurrencyModel getCurrency();

	public void setCurrency(CurrencyModel currency);

	public BigDecimal getValue();

	public void setValue(BigDecimal value);

}