package org.softgreen.sistcoop.ubigeo.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.ubigeo.client.representations.idm.DistritoRepresentation;

public class DistritoList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<DistritoRepresentation> distritos;

	public DistritoList(List<DistritoRepresentation> distritos) {
		this.distritos = distritos;
	}

	public List<DistritoRepresentation> getDistritos() {
		return distritos;
	}

	public void setDistritos(List<DistritoRepresentation> distritos) {
		this.distritos = distritos;
	}

}
