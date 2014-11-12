package org.softgreen.sistcoop.ubigeo.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.ubigeo.client.representations.idm.DepartamentoRepresentation;

public class DepartamentoList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<DepartamentoRepresentation> departamentos;

	public DepartamentoList(List<DepartamentoRepresentation> departamentos) {
		this.departamentos = departamentos;
	}

	public List<DepartamentoRepresentation> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<DepartamentoRepresentation> departamentos) {
		this.departamentos = departamentos;
	}

}
