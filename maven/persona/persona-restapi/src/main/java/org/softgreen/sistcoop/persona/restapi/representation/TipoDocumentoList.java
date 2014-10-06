package org.softgreen.sistcoop.persona.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.persona.client.representations.idm.TipoDocumentoRepresentation;

public class TipoDocumentoList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<TipoDocumentoRepresentation> countries;

	public TipoDocumentoList(List<TipoDocumentoRepresentation> tiposDocumento) {
		this.countries = tiposDocumento;
	}

	public List<TipoDocumentoRepresentation> getCountries() {
		return countries;
	}

	public void setCountries(List<TipoDocumentoRepresentation> countries) {
		this.countries = countries;
	}

}
