package org.softgreen.sistcoop.organizacion.client.models;

import java.util.List;

import javax.ejb.Local;

import org.softgreen.sistcoop.organizacion.client.providers.Provider;

/**
 * Tested
 */

@Local
public interface AgenciaProvider extends Provider {

	AgenciaModel addAgencia(SucursalModel sucursal, String codigo,
			String abreviatura, String denominacion, String ubigeo);

	boolean removeAgencia(AgenciaModel agenciaModel);

	AgenciaModel getAgenciaById(Integer id);

	AgenciaModel getAgenciaByCodigo(String codigo);

	List<AgenciaModel> getAgencias();

	List<AgenciaModel> getAgencias(boolean estado);

}
