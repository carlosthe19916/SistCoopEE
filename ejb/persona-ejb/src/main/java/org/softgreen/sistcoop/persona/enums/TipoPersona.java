package org.softgreen.sistcoop.persona.enums;

import org.softgreen.sistcoop.persona.util.LookupUtil;

public enum TipoPersona {
	/**
	 * Persona natural.
	 */
	NATURAL,

	/**
	 * Persona juridica.
	 */
	JURIDICA;

	/**
	 * Devuelve un Sexo segun la cadena enviada.
	 */
	static public TipoPersona lookup(String id) {
		return LookupUtil.lookup(TipoPersona.class, id.toUpperCase());
	}
}
