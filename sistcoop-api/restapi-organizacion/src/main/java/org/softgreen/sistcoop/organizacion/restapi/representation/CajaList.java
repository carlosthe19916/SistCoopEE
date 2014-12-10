package org.softgreen.sistcoop.organizacion.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.organizacion.client.representations.idm.CajaRepresentation;

public class CajaList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<CajaRepresentation> cajas;

	public CajaList(List<CajaRepresentation> cajas) {
		this.cajas = cajas;
	}

	public List<CajaRepresentation> getCajas() {
		return cajas;
	}

	public void setCajas(List<CajaRepresentation> cajas) {
		this.cajas = cajas;
	}

}
