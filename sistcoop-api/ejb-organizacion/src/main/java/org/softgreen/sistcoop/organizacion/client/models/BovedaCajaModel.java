package org.softgreen.sistcoop.organizacion.client.models;

// Generated 02-may-2014 11:48:28 by Hibernate Tools 4.0.0

import java.math.BigDecimal;

/**
 * Tested
 */

public interface BovedaCajaModel extends Model {

	public Integer getId();

	public BigDecimal getSaldo();

	public void setSaldo(BigDecimal saldo);

	public BovedaModel getBoveda();

	public CajaModel getCaja();

}
