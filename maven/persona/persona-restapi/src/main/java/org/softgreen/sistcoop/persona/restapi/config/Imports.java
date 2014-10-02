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
	@EJB(lookup = "java:global/persona-ejb/JpaTipoDocumentoProvider!org.softgreen.sistcoop.persona.client.models.TipoDocumentoProvider")
	private TipoDocumentoProvider tipoDocumentoProvider;

	@SuppressWarnings("unused")
	@Produces
	@EJB(lookup = "java:global/persona-ejb/JpaPersonaNaturalProvider!org.softgreen.sistcoop.persona.client.models.PersonaNaturalProvider")
	private PersonaNaturalProvider personaNaturalProvider;

	@SuppressWarnings("unused")
	@Produces
	@EJB(lookup = "java:global/persona-ejb/JpaPersonaJuridicaProvider!org.softgreen.sistcoop.persona.client.models.PersonaJuridicaProvider")
	private PersonaJuridicaProvider personaJuridicaProvider;

	@SuppressWarnings("unused")
	@Produces
	@EJB(lookup = "java:global/persona-ejb/JpaAccionistaProvider!org.softgreen.sistcoop.persona.client.models.AccionistaProvider")
	private AccionistaProvider accionistaProvider;

	private Imports() {
		// Disable instantiation of this class
	}

}