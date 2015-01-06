package org.softgreen.sistcoop.organizacion.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.organizacion.client.representations.idm.DetalleHistorialRepresentation;

public class DetalleHistorialList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<DetalleHistorialRepresentation> detalleHistorial;

	public DetalleHistorialList(List<DetalleHistorialRepresentation> detalleHistorial) {
		this.detalleHistorial = detalleHistorial;
	}

	public List<DetalleHistorialRepresentation> getDetalleHistorial() {
		return detalleHistorial;
	}

	public void setDetalleHistorial(List<DetalleHistorialRepresentation> detalleHistorial) {
		this.detalleHistorial = detalleHistorial;
	}

}
