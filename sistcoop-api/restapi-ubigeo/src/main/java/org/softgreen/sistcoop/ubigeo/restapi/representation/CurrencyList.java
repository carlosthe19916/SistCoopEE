package org.softgreen.sistcoop.ubigeo.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.ubigeo.client.representations.idm.CurrencyRepresentation;

public class CurrencyList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<CurrencyRepresentation> currencies;

	public CurrencyList(List<CurrencyRepresentation> currencies) {
		this.currencies = currencies;
	}

	public List<CurrencyRepresentation> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<CurrencyRepresentation> currencies) {
		this.currencies = currencies;
	}

}
