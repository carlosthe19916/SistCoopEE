package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;
import java.util.Date;

public interface TransaccionBancariaModel extends Model {

	Long getId();
	Long getNumeroOperacion();
	Date getFecha();
	Date getHora();
	boolean getEstado();
	void setEstado(boolean estado);
	String getObservacion();
	void setObservacion();

	String getNumeroCuenta();
	BigDecimal getMonto();
	String getReferencia();
	BigDecimal getSaldoDisponible();

}
