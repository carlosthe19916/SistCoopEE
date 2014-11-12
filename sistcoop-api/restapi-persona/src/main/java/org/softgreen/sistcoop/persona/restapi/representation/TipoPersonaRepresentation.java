package org.softgreen.sistcoop.persona.restapi.representation;

public class TipoPersonaRepresentation {

	String denominacion;

	public TipoPersonaRepresentation(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
}
