package org.softgreen.sistcoop.persona.enums;

import org.softgreen.sistcoop.persona.util.LookupUtil;

public enum EstadoCivil {
	SOLTERO, CASADO, DIVORCIADO, VIUDO;
	static public EstadoCivil lookup(String id) {
		return LookupUtil.lookup(EstadoCivil.class, id.toUpperCase());
	}
}
