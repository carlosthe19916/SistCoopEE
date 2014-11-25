package org.softgreen.sistcoop.organizacion.managers;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BovedaManager {

	public boolean desactivarBoveda(BovedaModel model) {
		if (model.isAbierto())
			throw new EJBException("Boveda abierta, no se puede desactivar");

		model.setEstado(false);
		model.setEstadoMovimiento(false);
		model.commit();

		// verificar saldo de boveda este en 0
		model.getBovedaCaja(cajaModel);
		
		// verificar que todas las cajas asociadas a la boveda esten cerradas

		// eliminar todas las relaciones bovedacaja

		// commitAll();
		return false;
	}

	public void abrir(BovedaModel bovedaModel) {

	}

	public void cerrar(BovedaModel bovedaModel) {

	}

	public void congelar(BovedaModel bovedaModel) {

	}

	public void descongelar(BovedaModel bovedaModel) {

	}
}
