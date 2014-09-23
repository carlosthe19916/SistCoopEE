package org.softgreen.persona.model.type;

import org.softgreen.persona.model.util.LookupUtil;

public enum TipoEmpresa {
	PRIVADA, ESTATAL, MIXTA, OTROS;
	static public TipoEmpresa lookup(String id) {
		return LookupUtil.lookup(TipoEmpresa.class, id.toUpperCase());
	}
}
