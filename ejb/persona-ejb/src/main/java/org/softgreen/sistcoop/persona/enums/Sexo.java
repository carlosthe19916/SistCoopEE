package org.softgreen.sistcoop.persona.enums;

import org.softgreen.sistcoop.persona.util.LookupUtil;

public enum Sexo {
	MASCULINO, FEMENINO;
	/***
	 * Get tipoPersona
	 */
	static public Sexo lookup(String id) {
		return LookupUtil.lookup(Sexo.class, id.toUpperCase());
	}
}
