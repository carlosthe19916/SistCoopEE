package org.softgreen.sistcoop.persona.restapi.representation;

import java.io.Serializable;
import java.util.List;

public class TipoPersonaList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<TipoPersonaRepresentation> tiposPersona;

	public TipoPersonaList(List<TipoPersonaRepresentation> tiposPersona) {
		this.tiposPersona = tiposPersona;
	}

	public List<TipoPersonaRepresentation> getTiposPersona() {
		return tiposPersona;
	}

	public void setTiposPersona(List<TipoPersonaRepresentation> tiposPersona) {
		this.tiposPersona = tiposPersona;
	}

}
