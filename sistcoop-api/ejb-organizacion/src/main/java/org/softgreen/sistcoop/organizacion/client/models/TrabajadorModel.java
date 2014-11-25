package org.softgreen.sistcoop.organizacion.client.models;

import java.util.List;

public interface TrabajadorModel extends Model {

	Integer getId();

	String getTipoDocumento();

	void setTipoDocumento(String tipoDocumento);

	String getNumeroDocumento();

	void setNumeroDocumento(String numeroDocumento);

	String getUsuario();

	void setUsuario(String usuario);

	boolean getEstado();

	void setEstado(boolean estado);

	AgenciaModel getAgencia();

	void setAgencia(AgenciaModel agenciaModel);

	List<TrabajadorCajaModel> getTrabajadorCajas();

}