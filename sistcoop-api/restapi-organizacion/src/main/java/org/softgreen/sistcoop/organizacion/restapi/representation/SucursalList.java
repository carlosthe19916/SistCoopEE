package org.softgreen.sistcoop.organizacion.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.organizacion.client.representations.idm.SucursalRepresentation;

public class SucursalList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SucursalRepresentation> sucursales;

	public SucursalList(List<SucursalRepresentation> sucursales) {
		this.sucursales = sucursales;
	}

	public List<SucursalRepresentation> getSucursales() {
		return sucursales;
	}

	public void setSucursales(List<SucursalRepresentation> sucursales) {
		this.sucursales = sucursales;
	}

}
