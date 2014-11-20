package org.softgreen.sistcoop.organizacion.client.models;

public interface CajaModel extends Model {

	Integer getId();

	String getDenominacion();

	void setDenominacion(String denominacion);

	boolean isAbierto();

	void setAbierto(boolean abierto);

	boolean getEstadoMovimiento();

	void setEstadoMovimiento(boolean estadoMovimiento);

	boolean getEstado();

	void setEstado(boolean estado);

	AgenciaModel getAgencia();

}