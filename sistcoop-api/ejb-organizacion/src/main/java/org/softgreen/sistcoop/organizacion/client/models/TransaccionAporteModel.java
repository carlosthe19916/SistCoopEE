package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;
import java.util.Date;

public interface TransaccionAporteModel extends Model {

	Long getId();
	Long getNumeroOperacion();
	Date getFecha();
	Date getHora();
	boolean getEstado();
	void setEstado(boolean estado);
	String getObservacion();
	void setObservacion();

	String getNumeroCuenta();
	String getMoneda();
	int getAnio();
	int getMes();
	BigDecimal getMonto();
	BigDecimal getSaldoDisponible();

}
