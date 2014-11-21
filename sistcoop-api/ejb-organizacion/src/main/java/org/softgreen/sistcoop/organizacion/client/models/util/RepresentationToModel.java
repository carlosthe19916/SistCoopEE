package org.softgreen.sistcoop.organizacion.client.models.util;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.AgenciaProvider;
import org.softgreen.sistcoop.organizacion.client.models.SucursalModel;
import org.softgreen.sistcoop.organizacion.client.models.SucursalProvider;
import org.softgreen.sistcoop.organizacion.client.representations.idm.AgenciaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.SucursalRepresentation;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RepresentationToModel {

	public SucursalModel createSucursal(SucursalRepresentation rep, SucursalProvider provider) {
		SucursalModel model = provider.addSucursal(rep.getAbreviatura(), rep.getDenominacion(), rep.getUbigeo());
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

}
