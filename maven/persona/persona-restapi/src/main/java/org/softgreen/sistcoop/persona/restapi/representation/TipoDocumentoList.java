package org.softgreen.sistcoop.persona.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.persona.client.representations.idm.TipoDocumentoRepresentation;

public class TipoDocumentoList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<TipoDocumentoRepresentation> tiposDocumento;

	public TipoDocumentoList(List<TipoDocumentoRepresentation> tiposDocumento) {
		this.tiposDocumento = tiposDocumento;
	}

	public List<TipoDocumentoRepresentation> getTiposDocumento() {
		return tiposDocumento;
	}

	public void setTiposDocumento(List<TipoDocumentoRepresentation> tiposDocumento) {
		this.tiposDocumento = tiposDocumento;
	}

}
