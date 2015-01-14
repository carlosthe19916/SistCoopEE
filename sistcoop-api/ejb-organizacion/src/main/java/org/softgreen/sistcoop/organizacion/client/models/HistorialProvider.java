package org.softgreen.sistcoop.organizacion.client.models;

import javax.ejb.Local;

import org.softgreen.sistcoop.organizacion.client.providers.Provider;

@Local
public interface HistorialProvider extends Provider {

	HistorialModel addHistorial(BovedaCajaModel bovedaCajaModel);

	HistorialModel addHistorial(BovedaModel bovedaModel);

	boolean removeHistorial(HistorialModel HistorialModel);

}
