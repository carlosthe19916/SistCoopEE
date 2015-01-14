package org.softgreen.sistcoop.organizacion.managers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialProvider;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialProvider;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaProvider;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CajaManager {

	@Inject
	protected BovedaCajaProvider bovedaCajaProvider;

	@Inject
	protected TrabajadorCajaProvider trabajadorCajaProvider;

	@Inject
	protected HistorialProvider historialProvider;

	@Inject
	protected DetalleHistorialProvider detalleHistorialProvider;
		
	public BovedaCajaModel addBoveda(CajaModel cajaModel, BovedaModel bovedaModel) {
		BovedaCajaModel bovedaCajaModel = bovedaCajaProvider.addBovedaCaja(bovedaModel, cajaModel);
		return bovedaCajaModel;
	}

	public void desactivarBovedaCaja(BovedaCajaModel bovedaCajaModel) {
		BovedaModel bovedaModel = bovedaCajaModel.getBoveda();
		CajaModel cajaModel = bovedaCajaModel.getCaja();
		if (bovedaModel.isAbierto())
			throw new EJBException("Boveda abierta, no se puede desactivar");
		if (cajaModel.isAbierto())
			throw new EJBException("Caja abierta, no se puede desactivar");

		BigDecimal saldo = bovedaCajaModel.getSaldo();
		if (saldo.compareTo(BigDecimal.ZERO) != 0) {
			throw new EJBException("La caja tiene saldo asignado diferente de cero.");
		}

		bovedaCajaModel.setEstado(false);
		bovedaCajaModel.commit();
	}

	public void desactivarCaja(CajaModel model) {
		if (model.isAbierto())
			throw new EJBException("Caja abierta, no se puede desactivar");

		List<BovedaCajaModel> list = model.getBovedaCajas();
		for (BovedaCajaModel bovCajModel : list) {
			desactivarBovedaCaja(bovCajModel);

			BigDecimal saldo = bovCajModel.getSaldo();
			if (saldo.compareTo(BigDecimal.ZERO) != 0) {
				throw new EJBException("La caja tiene saldo asignado diferente de cero.");
			}
		}

		List<TrabajadorCajaModel> list2 = model.getTrabajadorCajas();
		for (TrabajadorCajaModel trabCajModel : list2) {
			trabajadorCajaProvider.removeTrabajadorCaja(trabCajModel);
		}

		model.setEstado(false);
		model.setEstadoMovimiento(false);
		model.commit();

	}

	public void abrir(CajaModel cajaModel) {
		if (cajaModel.isAbierto()) {
			throw new EJBException("Caja abierta, no se puede abrir nuevamente.");
		}
		if (cajaModel.getEstadoMovimiento()) {
			throw new EJBException("Caja descongelada, no se puede abrir.");
		}
		if (cajaModel.getEstado()) {
			throw new EJBException("Caja inactiva, no se puede abrir.");
		}

		List<BovedaCajaModel> bovedaCajaModels = cajaModel.getBovedaCajas();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
			BovedaModel bovedaModel = bovedaCajaModel.getBoveda();
			if (!bovedaModel.isAbierto())
				throw new EJBException("Boveda cerrada, no se puede abrir");
		}

		List<HistorialModel> historialesActivos = new ArrayList<HistorialModel>();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
			HistorialModel historialModel = bovedaCajaModel.getHistorialActivo();
			if (historialModel != null)
				historialesActivos.add(historialModel);
		}

		boolean firstTime;
		if (historialesActivos.size() == 0) {
			firstTime = true;
		} else if (historialesActivos.size() == bovedaCajaModels.size()) {
			firstTime = false;
		} else {
			throw new EJBException("Error interno, existen cajas que no tienen historiales. Pongase en contacto con el area de sistemas.");
		}

		Calendar calendar = Calendar.getInstance();
		if (firstTime) {
			for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
				BovedaModel bovedaModel = bovedaCajaModel.getBoveda();
				String moneda = bovedaModel.getMoneda();
				ss
			}			
		} else {
			for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
				HistorialModel historialActivoModel = bovedaCajaModel.getHistorialActivo();

				List<DetalleHistorialModel> detalleHistorialActivoModels = historialActivoModel.getDetalle();

				historialActivoModel.setEstado(false);
				historialActivoModel.setFechaCierre(calendar.getTime());
				historialActivoModel.setHoraCierre(calendar.getTime());

				HistorialModel historialNewModel = historialProvider.addHistorial(bovedaCajaModel);
				for (DetalleHistorialModel detalleHistorialActivoModel : detalleHistorialActivoModels) {
					int cantidad = detalleHistorialActivoModel.getCantidad();
					BigDecimal valor = detalleHistorialActivoModel.getValor();
					detalleHistorialProvider.addDetalleHistorial(historialNewModel, cantidad, valor);
				}

				historialActivoModel.commit();
			}
		}

	}

	public void cerrar(CajaModel cajaModel) {

	}

	public void congelar(CajaModel cajaModel) {

	}

	public void descongelar(CajaModel cajaModel) {

	}

}
