package org.softgreen.sistcoop.persona.enums;

import org.softgreen.sistcoop.persona.util.LookupUtil;

public enum TipoEmpresa {
	PRIVADA, ESTATAL, MIXTA, OTROS;
	static public TipoEmpresa lookup(String id) {
		return LookupUtil.lookup(TipoEmpresa.class, id.toUpperCase());
	}
}
