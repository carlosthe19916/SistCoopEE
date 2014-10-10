package org.softgreen.sistcoop.persona.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.persona.client.representations.idm.PersonaJuridicaRepresentation;

public class PersonaJuridicaList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<PersonaJuridicaRepresentation> personasJuridicas;

	public PersonaJuridicaList(
			List<PersonaJuridicaRepresentation> personasJuridicas) {
		this.personasJuridicas = personasJuridicas;
	}

	public List<PersonaJuridicaRepresentation> getPersonasJuridicas() {
		return personasJuridicas;
	}

	public void setPersonasJuridicas(
			List<PersonaJuridicaRepresentation> personasJuridicas) {
		this.personasJuridicas = personasJuridicas;
	}

}
