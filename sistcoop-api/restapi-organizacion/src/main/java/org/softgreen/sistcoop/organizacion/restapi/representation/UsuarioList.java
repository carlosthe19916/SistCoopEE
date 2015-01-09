package org.softgreen.sistcoop.organizacion.restapi.representation;

import java.io.Serializable;
import java.util.List;

import org.softgreen.sistcoop.organizacion.client.representations.idm.UsuarioRepresentation;

public class UsuarioList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<UsuarioRepresentation> usuarios;

	public UsuarioList(List<UsuarioRepresentation> usuarios) {
		this.usuarios = usuarios;
	}

	public List<UsuarioRepresentation> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioRepresentation> usuarios) {
		this.usuarios = usuarios;
	}

}
