package org.softgreen.sistcoop.persona.restapi.representation;

import java.io.Serializable;
import java.util.List;

public class EstadoCivilList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<EstadoCivilRepresentation> estadosCiviles;

	public EstadoCivilList(List<EstadoCivilRepresentation> estadosCiviles) {
		this.estadosCiviles = estadosCiviles;
	}

	public List<EstadoCivilRepresentation> getEstadosCiviles() {
		return estadosCiviles;
	}

	public void setEstadosCiviles(List<EstadoCivilRepresentation> estadosCiviles) {
		this.estadosCiviles = estadosCiviles;
	}

}
