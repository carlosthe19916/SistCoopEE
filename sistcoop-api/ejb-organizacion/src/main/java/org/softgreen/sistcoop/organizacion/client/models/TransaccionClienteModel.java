package org.softgreen.sistcoop.organizacion.client.models;

import java.util.Date;
import java.util.List;

public interface TransaccionClienteModel extends Model {

	Long getId();

	Long getNumeroOperacion();

	Date getFecha();

	Date getHora();

	boolean getEstado();

	void setEstado();

	String getObservacion();

	void setObservacion();

	HistorialCajaModel getHistorial();

	List<DetalleTransaccionClienteModel> getDetalle();

}
