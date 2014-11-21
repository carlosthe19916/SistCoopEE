package org.softgreen.sistcoop.organizacion.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.organizacion.client.representations.idm.AgenciaRepresentation;

public class AgenciaList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<AgenciaRepresentation> agencias;

	public AgenciaList(List<AgenciaRepresentation> agencias) {
		this.agencias = agencias;
	}

	public List<AgenciaRepresentation> getAgencias() {
		return agencias;
	}

	public void setAgencias(List<AgenciaRepresentation> agencias) {
		this.agencias = agencias;
	}

}
