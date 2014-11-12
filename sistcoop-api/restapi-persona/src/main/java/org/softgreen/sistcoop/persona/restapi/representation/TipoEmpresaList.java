package org.softgreen.sistcoop.persona.restapi.representation;

import java.io.Serializable;
import java.util.List;

public class TipoEmpresaList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<TipoEmpresaRepresentation> tiposEmpresa;

	public TipoEmpresaList(List<TipoEmpresaRepresentation> tiposEmpresa) {
		this.tiposEmpresa = tiposEmpresa;
	}

	public List<TipoEmpresaRepresentation> getTiposEmpresa() {
		return tiposEmpresa;
	}

	public void setTiposEmpresa(List<TipoEmpresaRepresentation> tiposEmpresa) {
		this.tiposEmpresa = tiposEmpresa;
	}

	

}
