package org.softgreen.sistcoop.organizacion.client.models;

import java.util.List;

import javax.ejb.Local;

import org.softgreen.sistcoop.organizacion.client.providers.Provider;

@Local
public interface EntidadProvider extends Provider {

	EntidadModel addEntidad(String abreviatura, String denominacion);

	boolean removeEntidad(EntidadModel EntidadModel);

	EntidadModel getEntidadById(Integer id);

	List<EntidadModel> getEntidades();

	List<EntidadModel> getEntidades(boolean estado);

}
