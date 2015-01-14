package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;

public interface TransaccionBovedaEntidadModel extends TransaccionInternaModel, Model {

	Long getId();

	BigDecimal getSaldoDisponible();

	String getOrigen();

	String getObservacion();

	void setObservacion(String observacion);

	HistorialBovedaModel getHistorialBoveda();

	EntidadModel getEntidad();
}
