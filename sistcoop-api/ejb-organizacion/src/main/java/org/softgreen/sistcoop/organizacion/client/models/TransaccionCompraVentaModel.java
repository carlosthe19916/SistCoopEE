package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;
import java.util.Date;

public interface TransaccionCompraVentaModel extends TransaccionInternaModel,
		Model {

	Long getId();

	Long getNumeroOperacion();

	Date getFecha();

	Date getHora();

	boolean getEstado();

	void setEstado(boolean estado);

	String getObservacion();

	void setObservacion();

	String getMonedaRecibida();

	String getMonedaEntregada();

	BigDecimal getMontoRecibido();

	BigDecimal getMontoEntregado();

	BigDecimal getTipoCambio();

	String getCliente();

	String getTipoTransaccion();

}
