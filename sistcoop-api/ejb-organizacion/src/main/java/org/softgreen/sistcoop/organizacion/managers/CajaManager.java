package org.softgreen.sistcoop.organizacion.managers;

import java.math.BigDecimal;
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
import org.softgreen.sistcoop.organizacion.client.models.HistorialCajaModel;
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
		if(cajaModel.isAbierto()){
			throw new EJBException("Caja abierta, no se puede abrir nuevamente.");
		}
		if(cajaModel.getEstadoMovimiento()){
			throw new EJBException("Caja descongelada, no se puede abrir.");
		}
		if(cajaModel.getEstado()){
			throw new EJBException("Caja inactiva, no se puede abrir.");
		}

		Calendar calendar = Calendar.getInstance();
		HistorialModel historialActivoModel = cajaModel.getHistorialActivo();
		if(historialActivoModel == null){
			
		} else {
			List<DetalleHistorialModel> detalleHistorialActivoModels = historialActivoModel.getDetalle();
			
			historialActivoModel.setEstado(false);
			historialActivoModel.setFechaCierre(calendar.getTime());
			historialActivoModel.setHoraCierre(calendar.getTime());
			
			HistorialModel historialModel = historialProvider.addHistorial(cajaModel);
			for (DetalleHistorialModel detalleHistorialActivoModel : detalleHistorialActivoModels) {
				int cantidad = detalleHistorialActivoModel.getCantidad();
				BigDecimal valor = detalleHistorialActivoModel.getValor();
				detalleHistorialProvider.addDetalleHistorial(historialModel, cantidad, valor);	
			}					
			
			historialActivoModel.commit();
		}		
		
	}

	public void cerrar(CajaModel cajaModel) {

	}

	public void congelar(CajaModel cajaModel) {

	}

	public void descongelar(CajaModel cajaModel) {

	}

	

}
