package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;

public interface TransaccionAporteModel extends TransaccionClienteModel, Model {

	String getNumeroCuenta();

	String getMoneda();

	int getAnio();

	int getMes();

	BigDecimal getMonto();

	BigDecimal getSaldoDisponible();

}
