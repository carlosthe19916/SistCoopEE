package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;
import java.util.Date;

public interface TransferenciaBancariaModel extends Model {

	Long getId();
	Long getNumeroOperacion();
	Date getFecha();
	Date getHora();
	boolean getEstado();
	void setEstado(boolean estado);
	String getObservacion();
	void setObservacion();

	//Set<DetalleTransaccionClienteModel> getDetalle();
	
	String getNumeroCuentaOrigen();
	String getNumeroCuentaDestino();
	BigDecimal getMonto();
	String getMoneda();
	BigDecimal getSaldoDisponibleOrigen();
	BigDecimal getSaldoDisponibleDestino();
	String getReferencia();
	
}
