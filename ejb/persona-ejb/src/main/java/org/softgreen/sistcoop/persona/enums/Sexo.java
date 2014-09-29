package org.softgreen.sistcoop.persona.enums;

import org.softgreen.sistcoop.persona.util.LookupUtil;

public enum Sexo {

	/**
	 * Masculino.
	 */

	MASCULINO,

	/**
	 * Femenino.
	 */
	FEMENINO;

	/**
     * Devuelve un Sexo segun la cadena enviada.
     */
	static public Sexo lookup(String id) {
		return LookupUtil.lookup(Sexo.class, id.toUpperCase());
	}
}
