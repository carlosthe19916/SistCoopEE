package org.softgreen.sistcoop.persona.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.persona.client.representations.idm.TipoDocumentoRepresentation;

public class TipoDocumentoList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<TipoDocumentoRepresentation> tiposDocumentos;

	public TipoDocumentoList(List<TipoDocumentoRepresentation> tiposDocumentos) {
		this.tiposDocumentos = tiposDocumentos;
	}

	public List<TipoDocumentoRepresentation> getTiposDocumentos() {
		return tiposDocumentos;
	}

	public void setTiposDocumentos(List<TipoDocumentoRepresentation> tiposDocumentos) {
		this.tiposDocumentos = tiposDocumentos;
	}

}
