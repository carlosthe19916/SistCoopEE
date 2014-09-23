package org.softgreen.persona.model.type;

import org.softgreen.persona.model.util.LookupUtil;

public enum EstadoCivil {
	SOLTERO, CASADO, DIVORCIADO, VIUDO;
	static public EstadoCivil lookup(String id) {
		return LookupUtil.lookup(EstadoCivil.class, id.toUpperCase());
	}
}
