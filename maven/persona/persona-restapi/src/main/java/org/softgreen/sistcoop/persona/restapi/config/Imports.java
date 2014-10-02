package org.softgreen.sistcoop.persona.restapi.config;

import javax.ejb.EJB;
import javax.enterprise.inject.Produces;

import org.softgreen.sistcoop.persona.client.models.CountryProvider;

public class Imports {

	@SuppressWarnings("unused")
	@Produces
	@EJB(lookup = "java:global/persona-ejb/JpaCountryProvider!org.softgreen.sistcoop.persona.client.models.CountryProvider")
	private CountryProvider countryProvider;
	

	private Imports() {
		// Disable instantiation of this class
	}

}