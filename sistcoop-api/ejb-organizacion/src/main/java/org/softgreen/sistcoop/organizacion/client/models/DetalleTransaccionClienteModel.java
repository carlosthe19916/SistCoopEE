package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;

import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TransaccionClienteEntity;

/**
 * Tested
 */
public interface DetalleTransaccionClienteModel extends Model {

	Long getId();

	BigDecimal getValor();

	void setValor(BigDecimal valor);

	int getCantidad();

	void setCantidad(int valor);

	TransaccionClienteModel getTransaccion();
}
