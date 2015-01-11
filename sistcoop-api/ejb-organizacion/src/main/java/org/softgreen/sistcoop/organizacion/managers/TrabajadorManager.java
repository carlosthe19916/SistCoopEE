package org.softgreen.sistcoop.organizacion.managers;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TrabajadorManager {

	@Inject
	protected TrabajadorCajaProvider trabajadorCajaProvider;

	public boolean desactivarTrabajador(TrabajadorModel model) {
		//error;
		return false;
	}

	public TrabajadorCajaModel setCaja(TrabajadorModel trabajadorModel, CajaModel cajaModel) {
		List<TrabajadorCajaModel> trabajadorCajaModels = trabajadorModel.getTrabajadorCajas();
		for (TrabajadorCajaModel trabajadorCajaModel : trabajadorCajaModels) {
			trabajadorCajaProvider.removeTrabajadorCaja(trabajadorCajaModel);
		}
		TrabajadorCajaModel trabajadorCajaModel = trabajadorCajaProvider.addTrabajadorCaja(cajaModel, trabajadorModel);
		return trabajadorCajaModel;
	}

}
