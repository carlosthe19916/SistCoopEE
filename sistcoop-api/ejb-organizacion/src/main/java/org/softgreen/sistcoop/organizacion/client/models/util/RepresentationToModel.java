package org.softgreen.sistcoop.organizacion.client.models.util;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.AgenciaProvider;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaProvider;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.SucursalModel;
import org.softgreen.sistcoop.organizacion.client.models.SucursalProvider;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorProvider;
import org.softgreen.sistcoop.organizacion.client.representations.idm.AgenciaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.CajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.SucursalRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.TrabajadorRepresentation;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RepresentationToModel {

	public SucursalModel createSucursal(SucursalRepresentation rep, SucursalProvider provider) {
		SucursalModel model = provider.addSucursal(rep.getAbreviatura(), rep.getDenominacion());
		return model;
	}

	public AgenciaModel createAgencia(SucursalModel sucursalModel, AgenciaRepresentation rep, AgenciaProvider agenciaProvider) {
		AgenciaModel agenciaModel = agenciaProvider.addAgencia(sucursalModel, 
				rep.getCodigo(), 
				rep.getAbreviatura(),
				rep.getDenominacion(), 
				rep.getUbigeo());
		return agenciaModel;
	}
	
	public BovedaModel createBoveda(AgenciaModel agenciaModel, BovedaRepresentation rep, BovedaProvider bovedaProvider) {
		BovedaModel bovedaModel = bovedaProvider.addBoveda(agenciaModel, 
				rep.getMoneda(), rep.getDenominacion());		
		return bovedaModel;
	}
	
	public CajaModel createCaja(AgenciaModel agenciaModel, CajaRepresentation rep, CajaProvider cajaProvider) {
		CajaModel cajaModel = cajaProvider.addCaja(agenciaModel, rep.getDenominacion());			
		return cajaModel;
	}
	
	public CajaModel createCaja(AgenciaModel agenciaModel, CajaRepresentation rep, BovedaProvider bovedaProvider, CajaProvider cajaProvider, BovedaCajaProvider bovedaCajaProvider) {
		CajaModel cajaModel = cajaProvider.addCaja(agenciaModel, rep.getDenominacion());
		List<BovedaRepresentation> bovedasRep = rep.getBovedas();
		for (BovedaRepresentation bovedaRepresentation : bovedasRep) {
			BovedaModel bovedaModel = bovedaProvider.getBovedaById(bovedaRepresentation.getId());
			bovedaCajaProvider.addBovedaCaja(bovedaModel, cajaModel);
		}
		return cajaModel;
	}
	
	public TrabajadorModel createTrabajador(AgenciaModel agenciaModel, TrabajadorRepresentation rep, TrabajadorProvider trabajadorProvider) {
		TrabajadorModel trabajadorModel = trabajadorProvider.addTrabajador(agenciaModel, 
				rep.getTipoDocumento(), 
				rep.getNumeroDocumento());		
		return trabajadorModel;
	} 

}
