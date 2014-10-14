package org.softgreen.sistcoop.ubigeo.client.models;

import java.math.BigDecimal;
import java.util.Date;

public interface ExchangeRateModel extends Model {

	public Long getId();

	public CurrencyModel getCurrencyOrigin();

	public void setCurrencyOrigin(CurrencyModel currencyOrigin);

	public CurrencyModel getCurrencyDestiny();

	public void setCurrencyDestiny(CurrencyModel currencyDestiny);

	public Date getFecha();

	public void setFecha(Date fecha);

	public BigDecimal getValor();

	public void setValor(BigDecimal valor);

}