package org.softgreen.sistcoop.organizacion.client.models;

import javax.ejb.Local;

import org.softgreen.sistcoop.organizacion.client.providers.Provider;

/**
 * Tested
 */

@Local
public interface CajaProvider extends Provider {

	CajaModel addCaja(AgenciaModel agenciaModel, String denominacion);

	boolean removeCaja(CajaModel CajaModel);

	CajaModel getCajaById(Integer id);

}
