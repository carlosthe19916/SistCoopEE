package org.softgreen.sistcoop.persona.models;

import org.softgreen.sistcoop.persona.enums.TipoPersona;

public interface TipoDocumentoModel {

	String getAbreviatura();
	
	void setAbreviatura(String abreviatura);

	String getDenominacion();

	void setDenominacion(String denominacion);

	int getCantidadCaracteres();

	void setCantidadCaracteres(int cantidadCaracteres);

	TipoPersona getTipoPersona();

	void setTipoPersona(TipoPersona tipoPersona);

}