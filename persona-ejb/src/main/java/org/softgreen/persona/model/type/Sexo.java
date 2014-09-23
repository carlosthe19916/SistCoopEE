package org.softgreen.persona.model.type;

import org.softgreen.persona.model.util.LookupUtil;

public enum Sexo {
	MASCULINO, FEMENINO;
	/***
	 * Get tipoPersona
	 */
	static public Sexo lookup(String id) {
		return LookupUtil.lookup(Sexo.class, id.toUpperCase());
	}
}
