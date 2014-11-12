package org.softgreen.sistcoop.persona.restapi.representation;

public class TipoEmpresaRepresentation {

	String denominacion;

	public TipoEmpresaRepresentation(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
}
