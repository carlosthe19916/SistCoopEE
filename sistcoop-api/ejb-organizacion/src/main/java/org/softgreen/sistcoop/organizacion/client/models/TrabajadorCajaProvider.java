package org.softgreen.sistcoop.organizacion.client.models;

import javax.ejb.Local;

import org.softgreen.sistcoop.organizacion.client.providers.Provider;

/**
 * Tested
 */

@Local
public interface TrabajadorCajaProvider extends Provider {

	TrabajadorCajaModel addTrabajadorCaja(CajaModel cajaModel, TrabajadorModel trabajadorModel);

	boolean removeTrabajadorCaja(TrabajadorCajaModel trabajadorCajaModel);

}
