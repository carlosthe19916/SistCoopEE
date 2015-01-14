package org.softgreen.sistcoop.organizacion.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.DetalleHistorialRepresentation;

public class DetalleHistorialCajaRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BovedaRepresentation boveda;
	private List<DetalleHistorialRepresentation> detalleHistorial;

	public DetalleHistorialCajaRepresentation(List<DetalleHistorialRepresentation> detalleHistorial) {
		this.detalleHistorial = detalleHistorial;
	}

	public List<DetalleHistorialRepresentation> getDetalleHistorial() {
		return detalleHistorial;
	}

	public void setDetalleHistorial(List<DetalleHistorialRepresentation> detalleHistorial) {
		this.detalleHistorial = detalleHistorial;
	}

	public BovedaRepresentation getBoveda() {
		return boveda;
	}

	public void setBoveda(BovedaRepresentation boveda) {
		this.boveda = boveda;
	}

}
