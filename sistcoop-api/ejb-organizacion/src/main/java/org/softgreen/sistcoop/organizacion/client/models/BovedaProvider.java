package org.softgreen.sistcoop.organizacion.client.models;

import javax.ejb.Local;

import org.softgreen.sistcoop.organizacion.client.providers.Provider;

/**
 * Tested
 */

@Local
public interface BovedaProvider extends Provider {

	BovedaModel addBoveda(AgenciaModel agenciaModel, String moneda,
			String denominacion);

	boolean removeBoveda(BovedaModel BovedaModel);

	BovedaModel getBovedaById(Integer id);

}
