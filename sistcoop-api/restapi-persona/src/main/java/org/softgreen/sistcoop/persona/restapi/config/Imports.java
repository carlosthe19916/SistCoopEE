package org.softgreen.sistcoop.persona.restapi.config;

import javax.ejb.EJB;
import javax.enterprise.inject.Produces;

import org.softgreen.sistcoop.persona.client.models.AccionistaProvider;
import org.softgreen.sistcoop.persona.client.models.PersonaJuridicaProvider;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalProvider;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoProvider;

public class Imports {

	@Produces
	@EJB
	private TipoDocumentoProvider tipoDocumentoProvider;

	@Produces
	@EJB
	private PersonaNaturalProvider personaNaturalProvider;

	@Produces
	@EJB
	private PersonaJuridicaProvider personaJuridicaProvider;

	@Produces
	@EJB
	private AccionistaProvider accionistaProvider;

	private Imports() {
		// Disable instantiation of this class
	}

}