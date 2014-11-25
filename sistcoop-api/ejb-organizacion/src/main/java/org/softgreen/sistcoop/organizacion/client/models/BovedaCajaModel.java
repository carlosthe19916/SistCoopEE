package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;

public interface BovedaCajaModel extends Model {

	public Integer getId();

	public BigDecimal getSaldo();

	public void setSaldo(BigDecimal saldo);

	public BovedaModel getBoveda();

	public CajaModel getCaja();

}
