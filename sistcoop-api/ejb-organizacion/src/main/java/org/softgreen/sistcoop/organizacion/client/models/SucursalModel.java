package org.softgreen.sistcoop.organizacion.client.models;

public interface SucursalModel extends Model {

	Integer getId();	

	String getDenominacion();

	void setDenominacion(String denominacion);

	String getAbreviatura();

	void setAbreviatura(String abreviatura);

	String getUbigeo();

	void setUbigeo(String ubigeo);

	boolean getEstado();

	void setEstado(boolean estado);

}