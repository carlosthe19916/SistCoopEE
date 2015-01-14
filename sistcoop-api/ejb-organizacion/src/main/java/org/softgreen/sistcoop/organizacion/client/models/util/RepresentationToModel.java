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

	/**
	 * Crear una sucursal a partir de un objeto SucursalRepresentation.
	 * 
	 * @param representation
	 * @param sucursalProvider
	 */
	public SucursalModel createSucursal(SucursalRepresentation rep, SucursalProvider provider) {
		SucursalModel model = provider.addSucursal(rep.getAbreviatura(), rep.getDenominacion());
		return model;
	}

	/**
	 * Crear una agencia a partir de un objeto AgenciaRepresentation.
	 * 
	 * @param sucursalModel
	 * @param representation
	 * @param agenciaProvider
	 */
	public AgenciaModel createAgencia(SucursalModel sucursalModel, AgenciaRepresentation rep, AgenciaProvider agenciaProvider) {
		AgenciaModel agenciaModel = agenciaProvider.addAgencia(sucursalModel, rep.getCodigo(), rep.getAbreviatura(), rep.getDenominacion(), rep.getUbigeo());
		return agenciaModel;
	}

	/**
	 * Crear una boveda a partir de un objeto BovedaRepresentation.
	 * 
	 * @param agenciaModel
	 * @param representation
	 * @param bovedaProvider
	 */
	public BovedaModel createBoveda(AgenciaModel agenciaModel, BovedaRepresentation rep, BovedaProvider bovedaProvider) {
		BovedaModel bovedaModel = bovedaProvider.addBoveda(agenciaModel, rep.getMoneda(), rep.getDenominacion());
		return bovedaModel;
	}

	/**
	 * Crear una caja a partir de un objeto CajaRepresentation.
	 * 
	 * @param agenciaModel
	 * @param representation
	 * @param cajaProvider
	 */
	public CajaModel createCaja(AgenciaModel agenciaModel, CajaRepresentation rep, CajaProvider cajaProvider) {
		CajaModel cajaModel = cajaProvider.addCaja(agenciaModel, rep.getDenominacion());
		return cajaModel;
	}

	/**
	 * Crear una caja y asigna bovedas a la misma a partir de un objeto
	 * CajaRepresentation.
	 * 
	 * @param agenciaModel
	 * @param representation
	 * @param bovedaProvider
	 * @param cajaProvider
	 * @param bovedaCajaProvider
	 */
	public CajaModel createCaja(AgenciaModel agenciaModel, CajaRepresentation rep, BovedaProvider bovedaProvider, CajaProvider cajaProvider, BovedaCajaProvider bovedaCajaProvider) {
		CajaModel cajaModel = cajaProvider.addCaja(agenciaModel, rep.getDenominacion());
		List<BovedaRepresentation> bovedasRep = rep.getBovedas();
		for (BovedaRepresentation bovedaRepresentation : bovedasRep) {
			BovedaModel bovedaModel = bovedaProvider.getBovedaById(bovedaRepresentation.getId());
			bovedaCajaProvider.addBovedaCaja(bovedaModel, cajaModel);
		}
		return cajaModel;
	}

	/**
	 * Crear un trabajador a partir de un objeto TrabajadorRepresentation sin un
	 * Usuario.
	 * 
	 * @param agenciaModel
	 * @param representation
	 * @param trabajadorProvider
	 */
	public TrabajadorModel createTrabajador(AgenciaModel agenciaModel, TrabajadorRepresentation rep, TrabajadorProvider trabajadorProvider) {
		TrabajadorModel trabajadorModel = trabajadorProvider.addTrabajador(agenciaModel, rep.getTipoDocumento(), rep.getNumeroDocumento());
		return trabajadorModel;
	}

}
