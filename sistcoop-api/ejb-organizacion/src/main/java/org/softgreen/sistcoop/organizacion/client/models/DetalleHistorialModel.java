package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;

public interface DetalleHistorialModel extends Model {

	Long getId();

	BigDecimal getValor();

	int getCantidad();

	void setCantidad(int cantidad);

}
