package org.softgreen.sistcoop.persona.clien.enums;

import org.softgreen.sistcoop.persona.client.util.LookupUtil;

public enum TipoEmpresa {

	/**
	 * Empresa privada.
	 */
	PRIVADA,

	/**
	 * Empresa estatal.
	 */
	ESTATAL,

	/**
	 * Empresa mixta.
	 */
	MIXTA,

	/**
	 * Otro tipo de empresa.
	 */
	OTROS;

	/**
	 * Devuelve un Sexo segun la cadena enviada.
	 */
	static public TipoEmpresa lookup(String id) {
		return LookupUtil.lookup(TipoEmpresa.class, id.toUpperCase());
	}
}
