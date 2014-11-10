package org.softgreen.sistcoop.persona.restapi.representation;


public class SexoRepresentation {

	private String denominacion;
	
	public SexoRepresentation(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
}
