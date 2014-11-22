package org.softgreen.sistcoop.organizacion.client.models;

import javax.ejb.Local;

import org.softgreen.sistcoop.organizacion.client.providers.Provider;

/**
 * Tested
 */

@Local
public interface HistorialProvider extends Provider {

	HistorialModel addHistorial(CajaModel cajaModel);

	HistorialModel addHistorial(BovedaModel bovedaModel);

	boolean removeHistorial(HistorialModel HistorialModel);

	HistorialModel getHistorialById(Integer id);

}
