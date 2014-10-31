package org.softgreen.sistcoop.persona.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.persona.client.representations.idm.PersonaNaturalRepresentation;

public class PersonaNaturalList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<PersonaNaturalRepresentation> personasNaturales;

	public PersonaNaturalList(List<PersonaNaturalRepresentation> personasNaturales) {
		this.personasNaturales = personasNaturales;
	}

	public List<PersonaNaturalRepresentation> getPersonasNaturales() {
		return personasNaturales;
	}

	public void setPersonasNaturales(List<PersonaNaturalRepresentation> personasNaturales) {
		this.personasNaturales = personasNaturales;
	}

}
