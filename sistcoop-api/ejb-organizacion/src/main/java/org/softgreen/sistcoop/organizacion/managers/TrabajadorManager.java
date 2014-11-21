package org.softgreen.sistcoop.organizacion.managers;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.SucursalModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TrabajadorManager {

	public boolean desactivarTrabajador(TrabajadorModel model) {
		//error;
		return false;
	}

}
