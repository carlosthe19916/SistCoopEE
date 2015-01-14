package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;

public interface TransaccionCajaCajaModel extends TransaccionInternaModel, Model {

	Long getId();

	String getMoneda();

	BigDecimal getSaldoDisponibleOrigen();

	BigDecimal getSaldoDisponibleDestino();

	BigDecimal getMonto();

	HistorialBovedaCajaModel getHistorialOrigen();

	HistorialBovedaCajaModel getHistorialDestino();
}
