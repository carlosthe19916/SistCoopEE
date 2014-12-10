package org.softgreen.sistcoop.organizacion.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaRepresentation;

public class BovedaList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<BovedaRepresentation> bovedas;

	public BovedaList(List<BovedaRepresentation> bovedas) {
		this.bovedas = bovedas;
	}

	public List<BovedaRepresentation> getBovedas() {
		return bovedas;
	}

	public void setBovedas(List<BovedaRepresentation> bovedas) {
		this.bovedas = bovedas;
	}

}
