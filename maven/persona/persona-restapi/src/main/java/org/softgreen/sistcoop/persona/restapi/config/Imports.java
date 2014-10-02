package org.softgreen.sistcoop.persona.restapi.config;

import javax.ejb.EJB;
import javax.enterprise.inject.Produces;

import org.softgreen.sistcoop.persona.client.models.AccionistaProvider;
import org.softgreen.sistcoop.persona.client.models.PersonaJuridicaProvider;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalProvider;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoProvider;

public class Imports {

	@SuppressWarnings("unused")
	@Produces
	@EJB(lookup = "java:global/persona-ejb/org.softgreen.sistcoop.persona.ejb.models.jpa.JpaTipoDocumentoProvider!org.softgreen.sistcoop.persona.ejb.models.jpa.TipoDocumentoProvider")
	private TipoDocumentoProvider tipoDocumentoProvider;

	@SuppressWarnings("unused")
	@Produces
	@EJB(lookup = "java:global/persona-ejb/org.softgreen.sistcoop.persona.ejb.models.jpa.JpaPersonaNaturalProvider!org.softgreen.sistcoop.persona.ejb.models.jpa.PersonaNaturalProvider")
	private PersonaNaturalProvider personaNaturalProvider;

	@SuppressWarnings("unused")
	@Produces
	@EJB(lookup = "java:global/persona-ejb/org.softgreen.sistcoop.persona.ejb.models.jpa.JpaPersonaJuridicaProvider!org.softgreen.sistcoop.persona.ejb.models.jpa.PersonaJuridicaProvider")
	private PersonaJuridicaProvider personaJuridicaProvider;

	@SuppressWarnings("unused")
	@Produces
	@EJB(lookup = "java:global/persona-ejb/org.softgreen.sistcoop.persona.ejb.models.jpa.JpaAccionistaProvider!org.softgreen.sistcoop.persona.ejb.models.jpa.AccionistaProvider")
	private AccionistaProvider accionistaProvider;

	private Imports() {
		// Disable instantiation of this class
	}

}