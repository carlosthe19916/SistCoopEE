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
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.BovedaCajaEntity;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BovedaManager {

	public boolean desactivarBoveda(BovedaModel model) {
		if(model.isAbierto())
			return false;
		
		model.setEstado(false);
		model.setEstadoMovimiento(false);
		model.commit();
		
		//verificar saldo de boveda este en 0
		
		//verificar que todas las cajas asociadas a la boveda esten cerradas
		
		//eliminar todas las relaciones bovedacaja
		
		
		commitAll();
	}

}
