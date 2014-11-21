package org.softgreen.sistcoop.organizacion.client.models;

import java.util.Date;

public interface HistorialCajaModel extends Model {
	
	Long getId();
	Date getFechaApertura();
	Date getFechaCierre();
	void setFechaCierre(Date fechaCierre);
	Date getHoraApertura();
	Date getHoraCierre();
	void setHoraCierre(Date horaCierre);
	boolean getEstado();
	void setEstado(boolean estado);
	
}
