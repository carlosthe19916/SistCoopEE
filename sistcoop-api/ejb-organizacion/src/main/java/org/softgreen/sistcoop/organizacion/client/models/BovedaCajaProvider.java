package org.softgreen.sistcoop.organizacion.client.models;

import javax.ejb.Local;

import org.softgreen.sistcoop.organizacion.client.providers.Provider;

@Local
public interface BovedaCajaProvider extends Provider {

	BovedaCajaModel addBovedaCaja(BovedaModel bovedaModel, CajaModel cajaModel);

}
