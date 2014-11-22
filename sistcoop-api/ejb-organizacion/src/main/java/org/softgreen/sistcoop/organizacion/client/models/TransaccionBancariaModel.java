package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;

public interface TransaccionBancariaModel extends TransaccionClienteModel,
		Model {

	String getNumeroCuenta();

	BigDecimal getMonto();

	String getReferencia();

	BigDecimal getSaldoDisponible();

}
