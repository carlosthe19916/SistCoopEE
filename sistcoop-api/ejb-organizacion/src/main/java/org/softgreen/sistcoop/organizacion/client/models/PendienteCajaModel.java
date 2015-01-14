package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;
import java.util.Date;

public interface PendienteCajaModel extends Model {

	Long getId();

	Date getFecha();

	Date getHora();

	String getMoneda();

	BigDecimal getMonto();

	String getObservacion();

	String getTrabajador();

}
