package org.softgreen.sistcoop.organizacion.managers;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BovedaManager {

	@Inject
	protected BovedaCajaProvider bovedaCajaProvider;

	public void desactivarBoveda(BovedaModel model) {
		if (model.isAbierto())
			throw new EJBException("Boveda abierta, no se puede desactivar");

		model.setEstado(false);
		model.setEstadoMovimiento(false);
		model.commit();

		List<BovedaCajaModel> list = model.getBovedaCajas();
		for (BovedaCajaModel bovCajModel : list) {
			BigDecimal saldo = bovCajModel.getSaldo();
			CajaModel caja = bovCajModel.getCaja();
			if (caja.isAbierto()) {
				throw new EJBException("Caja abierta no se puede desactivar boveda.");
			}
			if (saldo.compareTo(BigDecimal.ZERO) != 0) {
				throw new EJBException("La boveda tiene saldo asignado a caja diferente de cero.");
			}			
			bovedaCajaProvider.removeBovedaCaja(bovCajModel);
		}
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
