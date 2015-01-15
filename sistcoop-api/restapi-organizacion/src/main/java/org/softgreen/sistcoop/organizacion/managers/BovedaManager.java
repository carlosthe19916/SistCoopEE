package org.softgreen.sistcoop.organizacion.managers;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialProvider;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialProvider;
import org.softgreen.sistcoop.ubigeo.client.models.CurrencyModel;
import org.softgreen.sistcoop.ubigeo.client.models.CurrencyProvider;
import org.softgreen.sistcoop.ubigeo.client.models.DenominationModel;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BovedaManager {

	@Inject
	protected BovedaCajaProvider bovedaCajaProvider;

	@Inject
	protected HistorialProvider historialProvider;

	@Inject
	protected DetalleHistorialProvider detalleHistorialProvider;
	
	@Inject
	protected CurrencyProvider currencyProvider;
	
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
			
			bovCajModel.setEstado(false);
			bovCajModel.commit();
		}
	}

	public void abrir(BovedaModel bovedaModel) {
		if (bovedaModel.isAbierto()) {
			throw new EJBException("Boveda abierta, no se puede abrir nuevamente.");
		}
		if (bovedaModel.getEstadoMovimiento()) {
			throw new EJBException("Boveda descongelada, no se puede abrir.");
		}
		if (!bovedaModel.getEstado()) {
			throw new EJBException("Boveda inactiva, no se puede abrir.");
		}

		List<BovedaCajaModel> bovedaCajaModels = bovedaModel.getBovedaCajas();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
			CajaModel cajaModel = bovedaCajaModel.getCaja();
			if (cajaModel.isAbierto())
				throw new EJBException("Caja asociada abierta, no se puede abrir");
		}				

		HistorialModel historialActivoModel = bovedaModel.getHistorialActivo();
		Calendar calendar = Calendar.getInstance();
		if (historialActivoModel == null) {
			HistorialModel historialNewModel = historialProvider.addHistorial(bovedaModel);			
			String moneda = bovedaModel.getMoneda();
			
			CurrencyModel currencyModel = currencyProvider.findByCode(moneda);
			Set<DenominationModel> denominationModels = currencyModel.getDenominations();
			for (DenominationModel denominationModel : denominationModels) {
				int cantidad = 0;
				BigDecimal valor = denominationModel.getValue();
				detalleHistorialProvider.addDetalleHistorial(historialNewModel, cantidad, valor);
			}			
		} else {			
			List<DetalleHistorialModel> detalleHistorialActivoModels = historialActivoModel.getDetalle();

			historialActivoModel.setEstado(false);
			historialActivoModel.setFechaCierre(calendar.getTime());
			historialActivoModel.setHoraCierre(calendar.getTime());

			HistorialModel historialNewModel = historialProvider.addHistorial(bovedaModel);
			for (DetalleHistorialModel detalleHistorialActivoModel : detalleHistorialActivoModels) {
				int cantidad = detalleHistorialActivoModel.getCantidad();
				BigDecimal valor = detalleHistorialActivoModel.getValor();
				detalleHistorialProvider.addDetalleHistorial(historialNewModel, cantidad, valor);
			}

			historialActivoModel.commit();
		}
		
		bovedaModel.setAbierto(true);
		bovedaModel.setEstadoMovimiento(false);
		bovedaModel.commit();
	}

	public void cerrar(BovedaModel bovedaModel) {

	}

	public void congelar(BovedaModel bovedaModel) {

	}

	public void descongelar(BovedaModel bovedaModel) {

	}
}
