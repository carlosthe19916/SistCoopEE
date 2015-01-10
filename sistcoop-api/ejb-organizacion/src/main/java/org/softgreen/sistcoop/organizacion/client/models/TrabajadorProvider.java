package org.softgreen.sistcoop.organizacion.client.models;

import javax.ejb.Local;

import org.softgreen.sistcoop.organizacion.client.providers.Provider;

@Local
public interface TrabajadorProvider extends Provider {

	TrabajadorModel addTrabajador(AgenciaModel agenciaModel, String tipoDocumento, String numeroDocumento);

	boolean removeTrabajador(TrabajadorModel trabajadorModel);
	
	TrabajadorModel getTrabajadorByUsuario(String usuario);
	
	TrabajadorModel getTrabajadorById(Integer id);

}
