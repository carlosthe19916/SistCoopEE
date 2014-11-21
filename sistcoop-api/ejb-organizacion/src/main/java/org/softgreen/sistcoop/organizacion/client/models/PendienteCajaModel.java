package org.softgreen.sistcoop.organizacion.client.models;

// Generated 02-may-2014 11:48:28 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;

public interface PendienteCajaModel extends Model {

	Long getId();

	Date getFecha();

	void setFecha(Date fecha);

	Date getHora();

	void setHora(Date hora);

	String getMoneda();

	void setMoneda(String hora);

	BigDecimal getMonto();

	void setMonto(BigDecimal hora);

	String getObservacion();

	void setObservacion(String hora);

	String getTrabajador();

	void setTrabajador(String trabajador);

}
