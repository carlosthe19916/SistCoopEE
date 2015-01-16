package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;

public interface BovedaCajaModel extends Model {

	Integer getId();

	BigDecimal getSaldo();

	void setSaldo(BigDecimal saldo);

	boolean getEstado();

	void setEstado(boolean estado);

	BovedaModel getBoveda();

	CajaModel getCaja();

	HistorialModel getHistorialActivo();
		
}
