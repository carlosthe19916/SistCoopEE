package org.softgreen.sistcoop.organizacion.client.models;

import java.util.List;

/**
 * Tested
 */

public interface AgenciaModel extends Model {

	Integer getId();

	String getCodigo();

	String getDenominacion();

	void setDenominacion(String denominacion);

	String getAbreviatura();

	void setAbreviatura(String abreviatura);

	String getUbigeo();

	void setUbigeo(String ubigeo);

	boolean getEstado();

	void setEstado(boolean estado);

	SucursalModel getSucursal();

	List<BovedaModel> getBovedas();
	
	List<BovedaModel> getBovedas(boolean estado);

	List<CajaModel> getCajas();
	
	List<CajaModel> getCajas(boolean estado);

	List<TrabajadorModel> getTrabajadores();

	

}