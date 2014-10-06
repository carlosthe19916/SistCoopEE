package org.softgreen.sistcoop.ubigeo.restapi.config;

import javax.ejb.EJB;
import javax.enterprise.inject.Produces;

import org.softgreen.sistcoop.ubigeo.client.models.CountryProvider;
import org.softgreen.sistcoop.ubigeo.client.models.UbigeoProvider;

public class Imports {

	@SuppressWarnings("unused")
	@Produces
	@EJB(lookup = "java:global/ubigeo-ejb/JpaCountryProvider!org.softgreen.sistcoop.ubigeo.client.models.CountryProvider")
	private CountryProvider countryProvider;

	@SuppressWarnings("unused")
	@Produces
	@EJB(lookup = "java:global/ubigeo-ejb/JpaUbigeoProvider!org.softgreen.sistcoop.ubigeo.client.models.ubigeoProvider")
	private UbigeoProvider ubigeoProvider;

	private Imports() {
		// Disable instantiation of this class
	}

}