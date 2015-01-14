package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;

public interface DetalleTransaccionInternaModel extends Model {

	Long getId();

	BigDecimal getValor();

	void setValor(BigDecimal valor);

	int getCantidad();

	void setCantidad(int valor);

	TransaccionInternaModel getTransaccion();
}
