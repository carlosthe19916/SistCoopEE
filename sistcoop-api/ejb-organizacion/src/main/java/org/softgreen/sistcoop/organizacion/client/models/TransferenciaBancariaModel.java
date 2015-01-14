package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;

public interface TransferenciaBancariaModel extends TransaccionClienteModel, Model {

	String getNumeroCuentaOrigen();

	String getNumeroCuentaDestino();

	BigDecimal getMonto();

	String getMoneda();

	BigDecimal getSaldoDisponibleOrigen();

	BigDecimal getSaldoDisponibleDestino();

	String getReferencia();

}
