package org.softgreen.sistcoop.organizacion.restapi.representation;

import java.io.Serializable;
import java.util.List;

public class DetalleHistorialCajaList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<DetalleHistorialCajaRepresentation> detalleHistorial;

	public DetalleHistorialCajaList(List<DetalleHistorialCajaRepresentation> detalleHistorial) {
		this.detalleHistorial = detalleHistorial;
	}

	public List<DetalleHistorialCajaRepresentation> getDetalleHistorial() {
		return detalleHistorial;
	}

	public void setDetalleHistorial(List<DetalleHistorialCajaRepresentation> detalleHistorial) {
		this.detalleHistorial = detalleHistorial;
	}

}
