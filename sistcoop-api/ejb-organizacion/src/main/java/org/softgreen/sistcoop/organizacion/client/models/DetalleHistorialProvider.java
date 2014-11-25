package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;

import javax.ejb.Local;

import org.softgreen.sistcoop.organizacion.client.providers.Provider;

/**
 * Tested
 */

@Local
public interface DetalleHistorialProvider extends Provider {

	DetalleHistorialModel addDetalleHistorial(HistorialModel historialModel, int cantidad, BigDecimal valor);

	DetalleHistorialModel getDetalleHistorialById(Long id);

}
