package org.softgreen.sistcoop.organizacion.client.models;

// Generated 02-may-2014 11:48:28 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;

/**
 * TransaccionCajaCaja generated by hbm2java
 */

public interface TransaccionCajaCajaModel extends Model {

	Long getId();
	String getMoneda();
	BigDecimal getSaldoDisponibleOrigen();
	BigDecimal getSaldoDisponibleDestino();
	BigDecimal getMonto();
	
	Date getFecha();
	Date getHora();
	boolean getEstado();
	void setEstado(boolean estado);
	String getObservacion();
	void setObservacion(String observacion);
	boolean getEstadoSolicitud();
	void setEstadoSolicitud(boolean estadoSolicitud);
	boolean getEstadoConfirmacion();
	void setEstadoConfirmacion(boolean estadoConfirmacion);

}
