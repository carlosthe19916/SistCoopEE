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
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaProvider;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CajaManager {

	@Inject
	protected BovedaCajaProvider bovedaCajaProvider;

	@Inject
	protected TrabajadorCajaProvider trabajadorCajaProvider;
	
	public BovedaCajaModel addBoveda(CajaModel cajaModel, BovedaModel bovedaModel){
		BovedaCajaModel bovedaCajaModel = bovedaCajaProvider.addBovedaCaja(bovedaModel, cajaModel);
		return bovedaCajaModel;
	}
	
	public void removeBovedaCaja(BovedaCajaModel bovedaCajaModel){
		bovedaCajaProvider.removeBovedaCaja(bovedaCajaModel);		
	}
	
	public void desactivarCaja(CajaModel model) {
		if (model.isAbierto())
			throw new EJBException("Caja abierta, no se puede desactivar");

		model.setEstado(false);
		model.setEstadoMovimiento(false);
		model.commit();

		List<BovedaCajaModel> list = model.getBovedaCajas();
		for (BovedaCajaModel bovCajModel : list) {
			BigDecimal saldo = bovCajModel.getSaldo();			
			if (saldo.compareTo(BigDecimal.ZERO) != 0) {
				throw new EJBException("La caja tiene saldo asignado diferente de cero.");
			}
			bovedaCajaProvider.removeBovedaCaja(bovCajModel);
		}
		
		List<TrabajadorCajaModel> list2 = model.getTrabajadorCajas();
		for (TrabajadorCajaModel trabCajModel : list2) {			
			trabajadorCajaProvider.removeTrabajadorCaja(trabCajModel);
		}
	}
	
	public void abrir(CajaModel cajaModel) {

	}

	public void cerrar(CajaModel cajaModel) {

	}

	public void congelar(CajaModel cajaModel) {

	}

	public void descongelar(CajaModel cajaModel) {

	}

	

}
