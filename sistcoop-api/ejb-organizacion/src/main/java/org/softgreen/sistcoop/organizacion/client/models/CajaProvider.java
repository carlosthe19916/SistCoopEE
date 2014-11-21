package org.softgreen.sistcoop.organizacion.client.models;

import java.util.List;

import javax.ejb.Local;

import org.softgreen.sistcoop.organizacion.client.providers.Provider;

/**
 * Tested
 */

@Local
public interface CajaProvider extends Provider {

	CajaModel addCaja(SucursalModel sucursal, String codigo, String abreviatura, String denominacion, String ubigeo);

	boolean removeCaja(CajaModel CajaModel);

	CajaModel getCajaById(Integer id);

	List<CajaModel> getCajas();

	List<CajaModel> getCajas(boolean estado);

}
