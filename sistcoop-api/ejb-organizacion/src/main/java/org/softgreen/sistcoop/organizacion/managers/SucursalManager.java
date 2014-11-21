package org.softgreen.sistcoop.organizacion.managers;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.SucursalModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SucursalManager {

	@Inject
	protected TrabajadorManager trabajadorManager;
	
	@Inject
	protected BovedaManager bovedaManager;
	
	@Inject
	protected CajaManager cajaManager;
	
	public boolean desactivarSucursal(SucursalModel model) {
		model.setEstado(false);
		model.commit();

		List<AgenciaModel> agencias = model.getAgencias();
		for (AgenciaModel agenciaModel : agencias) {
			boolean result = desactivarAgencia(agenciaModel);
			if (!result) {
				//rollback();
				break;
			}
		}
		return true;
	}

	public boolean desactivarAgencia(AgenciaModel model) {
		model.setEstado(false);
		model.commit();
		List<BovedaModel> bovedasModel = model.getBovedas();
		List<CajaModel> cajasModel = model.getCajas();
		List<TrabajadorModel> trajadoresModel = model.getTrabajadores();

		for (BovedaModel bovedaModel : bovedasModel) {
			boolean result = bovedaManager.desactivarBoveda(bovedaModel);
			if(!result){
				//rollback();
				break;
			}
		}
		for (CajaModel cajaModel : cajasModel) {
			boolean result = cajaManager.desactivarCaja(cajaModel);
			if(!result){
				//rollback();
				break;
			}
		}
		for (TrabajadorModel trabajadorModel : trajadoresModel) {
			boolean result = trabajadorManager.desactivarTrabajador(trabajadorModel);
			if(!result){
				//rollback();
				break;
			}
		}
		
		//commitAll();
		return false;
	}

}
