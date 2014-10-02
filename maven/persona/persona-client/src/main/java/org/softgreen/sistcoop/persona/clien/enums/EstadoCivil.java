package org.softgreen.sistcoop.persona.clien.enums;

import org.softgreen.sistcoop.persona.client.util.LookupUtil;

public enum EstadoCivil {
	/**
	 * Persona sin union civil.
	 */
	SOLTERO,

	/**
	 * Persona que contrajo union civil.
	 */
	CASADO,

	/**
	 * Persona que separada.
	 */
	DIVORCIADO,

	/**
	 * Persona cuyo conyugue fallecio.
	 */
	VIUDO;

	/**
	 * Devuelve un Sexo segun la cadena enviada.
	 */
	static public EstadoCivil lookup(String id) {
		return LookupUtil.lookup(EstadoCivil.class, id.toUpperCase());
	}
}
