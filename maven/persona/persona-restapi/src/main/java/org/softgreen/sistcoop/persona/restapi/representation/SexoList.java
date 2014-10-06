package org.softgreen.sistcoop.persona.restapi.representation;

import java.io.Serializable;
import java.util.List;

public class SexoList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SexoRepresentation> sexos;

	public SexoList(List<SexoRepresentation> Sexo) {
		this.sexos = Sexo;
	}

	public List<SexoRepresentation> getSexos() {
		return sexos;
	}

	public void setSexos(List<SexoRepresentation> sexos) {
		this.sexos = sexos;
	}

}
