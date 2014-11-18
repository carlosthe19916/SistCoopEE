package org.softgreen.sistcoop.persona.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.persona.client.representations.idm.AccionistaRepresentation;

public class AccionistaList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<AccionistaRepresentation> accionistas;

	public AccionistaList(List<AccionistaRepresentation> accionistas) {
		this.accionistas = accionistas;
	}

	public List<AccionistaRepresentation> getAccionistas() {
		return accionistas;
	}

	public void setAccionistas(List<AccionistaRepresentation> accionistas) {
		this.accionistas = accionistas;
	}

}
