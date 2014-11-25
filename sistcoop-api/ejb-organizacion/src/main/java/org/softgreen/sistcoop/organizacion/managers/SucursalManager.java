package org.softgreen.sistcoop.organizacion.managers;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

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
	
	public void desactivarSucursal(SucursalModel model) {			
		model.setEstado(false);
		model.commit();			
		List<AgenciaModel> agencias = model.getAgencias();
		for (AgenciaModel agenciaModel : agencias) {
			desactivarAgencia(agenciaModel);
		}    	           
	}

	public void desactivarAgencia(AgenciaModel model) {
		model.setEstado(false);
		model.commit();
		
		List<BovedaModel> bovedasModel = model.getBovedas();
		List<CajaModel> cajasModel = model.getCajas();
		List<TrabajadorModel> trajadoresModel = model.getTrabajadores();

		for (BovedaModel bovedaModel : bovedasModel) {
			bovedaManager.desactivarBoveda(bovedaModel);
		}
		for (CajaModel cajaModel : cajasModel) {
			cajaManager.desactivarCaja(cajaModel);			
		}
		for (TrabajadorModel trabajadorModel : trajadoresModel) {
			trabajadorManager.desactivarTrabajador(trabajadorModel);
		}
	}

}
