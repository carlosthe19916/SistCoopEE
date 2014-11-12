package org.softgreen.sistcoop.ubigeo.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.ubigeo.client.representations.idm.ProvinciaRepresentation;

public class ProvinciaList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ProvinciaRepresentation> provincias;

	public ProvinciaList(List<ProvinciaRepresentation> provincias) {
		this.provincias = provincias;
	}

	public List<ProvinciaRepresentation> getProvincias() {
		return provincias;
	}

	public void setProvincias(List<ProvinciaRepresentation> provincias) {
		this.provincias = provincias;
	}

}
