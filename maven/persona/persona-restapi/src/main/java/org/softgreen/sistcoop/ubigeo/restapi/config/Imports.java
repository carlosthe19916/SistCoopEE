package org.softgreen.sistcoop.ubigeo.restapi.config;

import javax.ejb.EJB;
import javax.enterprise.inject.Produces;

import org.softgreen.sistcoop.ubigeo.client.models.CountryProvider;

public class Imports {

	@SuppressWarnings("unused")
	@Produces
	@EJB(lookup = "java:global/ubigeo-ejb/JpaCountryProvider!org.softgreen.sistcoop.ubigeo.client.models.CountryProvider")
	private CountryProvider countryProvider;
	

	private Imports() {
		// Disable instantiation of this class
	}

}