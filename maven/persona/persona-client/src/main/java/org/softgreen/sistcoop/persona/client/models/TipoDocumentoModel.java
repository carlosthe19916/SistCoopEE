package org.softgreen.sistcoop.persona.client.models;

import org.softgreen.sistcoop.persona.clien.enums.TipoPersona;

public interface TipoDocumentoModel extends Model {

	String getAbreviatura();

	void setAbreviatura(String abreviatura);

	String getDenominacion();

	void setDenominacion(String denominacion);

	int getCantidadCaracteres();

	void setCantidadCaracteres(int cantidadCaracteres);

	TipoPersona getTipoPersona();

	void setTipoPersona(TipoPersona tipoPersona);

}