package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;

public interface TransaccionBovedaBovedaModel extends TransaccionInternaModel, Model {

	Long getId();

	BigDecimal getSaldoDisponibleOrigen();

	BigDecimal getSaldoDisponibleDestino();

	void setSaldoDisponibleDestino(BigDecimal saldoDisponibleDestino);

	String getOrigen();

	String getObservacion();

	void setObservacion(String observacion);

	HistorialBovedaModel getHistorialOrigen();

	HistorialBovedaModel getHistorialDestino();
}
