package org.softgreen.persona.model.type;

import org.softgreen.persona.model.util.LookupUtil;

public enum TipoPersona {
	/**
	 * Persona natural**/
	NATURAL, 
	
	/***
	 * Persona juridica*/
	JURIDICA;
	
	/***
	 * Get tipoPersona*/
	static public TipoPersona lookup(String id) {
		return LookupUtil.lookup(TipoPersona.class, id.toUpperCase());
	}
}
