package org.softgreen.sistcoop.ubigeo.client.representations.idm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ExchangeRateRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private CurrencyRepresentation currencyOrigin;
	private CurrencyRepresentation currencyDestiny;
	private Date fecha;
	private BigDecimal valor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CurrencyRepresentation getCurrencyOrigin() {
		return currencyOrigin;
	}

	public void setCurrencyOrigin(CurrencyRepresentation currencyOrigin) {
		this.currencyOrigin = currencyOrigin;
	}

	public CurrencyRepresentation getCurrencyDestiny() {
		return currencyDestiny;
	}

	public void setCurrencyDestiny(CurrencyRepresentation currencyDestiny) {
		this.currencyDestiny = currencyDestiny;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
