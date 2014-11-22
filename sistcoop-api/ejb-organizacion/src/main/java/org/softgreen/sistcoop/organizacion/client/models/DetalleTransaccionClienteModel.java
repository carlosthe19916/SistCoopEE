package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;

/**
 * Tested
 */
public interface DetalleTransaccionClienteModel extends Model {

	Long getId();

	BigDecimal getValor();

	int getCantidad();

	void setCantidad(int valor);

	TransaccionClienteModel getTransaccion();
}
