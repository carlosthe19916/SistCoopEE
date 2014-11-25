package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface HistorialModel extends Model {

	Long getId();

	Date getFechaApertura();

	Date getFechaCierre();

	void setFechaCierre(Date fechaCierre);

	Date getHoraApertura();

	Date getHoraCierre();

	void setHoraCierre(Date horaCierre);

	boolean getEstado();

	void setEstado(boolean estado);

	BigDecimal getSaldo();

	List<DetalleHistorialModel> getDetalle();
}
