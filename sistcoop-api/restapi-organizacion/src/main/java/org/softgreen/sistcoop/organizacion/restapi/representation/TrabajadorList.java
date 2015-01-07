package org.softgreen.sistcoop.organizacion.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.organizacion.client.representations.idm.TrabajadorRepresentation;

public class TrabajadorList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<TrabajadorRepresentation> trabajadores;

	public TrabajadorList(List<TrabajadorRepresentation> trabajadores) {
		this.trabajadores = trabajadores;
	}

	public List<TrabajadorRepresentation> getTrabajadores() {
		return trabajadores;
	}

	public void setTrabajadores(List<TrabajadorRepresentation> trabajadores) {
		this.trabajadores = trabajadores;
	}

}
