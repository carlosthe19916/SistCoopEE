package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;

/**
 * Tested
 */
public interface DetalleHistorialModel extends Model {

	Long getId();

	BigDecimal getValor();

	void setValor(BigDecimal valor);

	int getCantidad();

	void setCantidad(int valor);

	HistorialModel getHistorial();
}
