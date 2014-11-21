package org.softgreen.sistcoop.organizacion.client.models;

// Generated 02-may-2014 11:48:28 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;

/**
 * TransaccionBovedaCaja generated by hbm2java
 */

public interface TransaccionBovedaCajaModel extends Model {

	Long getId();

	Date getFecha();

	void setFecha(Date fecha);

	Date getHora();

	void setHora();

	boolean getEstado();

	void setEstado(boolean estado);

	boolean getEstadoSolicitud();

	void setEstadoSolicitud(boolean estadoSolicitud);

	boolean getEstadoConfirmacion();

	void setEstadoConfirmacion(boolean estadoConfirmacion);

	BigDecimal getSaldoDisponibleOrigen();

	void setSaldoDisponibleOrigen(BigDecimal saldoDisponibleOrigen);

	BigDecimal getSaldoDisponibleDestino();

	void setSaldoDisponibleDestino(BigDecimal saldoDisponibleDestino);

	String getOrigen();

	void setOrigen(String origen);

	String getObservacion();

	void setObservacion(String observacion);

}
