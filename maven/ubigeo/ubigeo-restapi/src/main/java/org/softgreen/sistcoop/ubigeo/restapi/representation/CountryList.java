package org.softgreen.sistcoop.ubigeo.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.ubigeo.client.representations.idm.CountryRepresentation;

public class CountryList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<CountryRepresentation> countries;

	public CountryList(List<CountryRepresentation> countries) {
		this.countries = countries;
	}

	public List<CountryRepresentation> getCountries() {
		return countries;
	}

	public void setCountries(List<CountryRepresentation> countries) {
		this.countries = countries;
	}

}
