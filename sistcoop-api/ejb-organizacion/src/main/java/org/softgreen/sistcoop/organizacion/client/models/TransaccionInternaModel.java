package org.softgreen.sistcoop.organizacion.client.models;

import java.util.Date;
import java.util.List;

public interface TransaccionInternaModel extends Model {

	Date getFecha();

	Date getHora();

	boolean getEstado();

	String getObservacion();

	boolean getEstadoSolicitud();

	boolean getEstadoConfirmacion();

	void setEstadoConfirmacion();

	List<DetalleTransaccionInternaModel> getDetalle();
}
