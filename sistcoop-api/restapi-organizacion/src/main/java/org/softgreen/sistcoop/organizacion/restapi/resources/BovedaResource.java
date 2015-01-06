package org.softgreen.sistcoop.organizacion.restapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaProvider;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialBovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.CajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.DetalleHistorialRepresentation;
import org.softgreen.sistcoop.organizacion.restapi.representation.CajaList;
import org.softgreen.sistcoop.organizacion.restapi.representation.DetalleHistorialList;

@Path("/bovedas")
@Stateless
public class BovedaResource {

	@Inject
	protected BovedaProvider bovedaProvider;
	
	@BadgerFish
	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public BovedaRepresentation getBovedaById(@PathParam("id") Integer id) {
		BovedaModel bovedaModel = bovedaProvider.getBovedaById(id);
		BovedaRepresentation bovedaRepresentation = ModelToRepresentation.toRepresentation(bovedaModel);
		return bovedaRepresentation;
	}
	
	@GET
	@Path("/{id}/cajas")
	@Produces({ "application/xml", "application/json" })
	public CajaList getCajasAsignadas(@PathParam("id") Integer id) {
		BovedaModel bovedaModel = bovedaProvider.getBovedaById(id);
		List<BovedaCajaModel> bovedaCajaList = bovedaModel.getBovedaCajas();
		List<CajaRepresentation> list = new ArrayList<CajaRepresentation>();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaList) {
			CajaModel cajaModel = bovedaCajaModel.getCaja();
			CajaRepresentation cajaRepresentation = new CajaRepresentation();
			cajaRepresentation.setId(cajaModel.getId());
			cajaRepresentation.setDenominacion(cajaModel.getDenominacion());
			cajaRepresentation.setAbierto(cajaModel.isAbierto());
			cajaRepresentation.setEstadoMovimiento(cajaModel.getEstadoMovimiento());
			cajaRepresentation.setEstado(cajaModel.getEstado());
			
			list.add(cajaRepresentation);
		}
		return new CajaList(list);
	}
	
	@GET
	@Path("/{id}/detalle")
	@Produces({ "application/xml", "application/json" })
	public DetalleHistorialList getDetalle(@PathParam("id") Integer id) {
		BovedaModel bovedaModel = bovedaProvider.getBovedaById(id);
		HistorialModel historialModel = bovedaModel.getHistorialActivo();
		if(historialModel != null){
			List<DetalleHistorialModel> detalleHistorialModel = historialModel.getDetalle();
			List<DetalleHistorialRepresentation> detalleHistorialRepresentations = new ArrayList<DetalleHistorialRepresentation>();
			for (DetalleHistorialModel detHistModel : detalleHistorialModel) {
				DetalleHistorialRepresentation detalleHistorialRepresentation = ModelToRepresentation.toRepresentation(detHistModel);
				detalleHistorialRepresentations.add(detalleHistorialRepresentation);
			}
			return new DetalleHistorialList(detalleHistorialRepresentations);
		} else {
			return null;
		}				
	}
	
	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public void update(@PathParam("id") Integer id, BovedaRepresentation rep) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		model.setDenominacion(rep.getDenominacion());		
		model.commit();
	}
	
	@POST
	@Path("/{id}/abrir")
	@Produces({ "application/xml", "application/json" })
	public void abrir(@PathParam("id") Integer id) {
		
	}
	
	@POST
	@Path("/{id}/cerrar")
	@Produces({ "application/xml", "application/json" })
	public void cerrar(@PathParam("id") Integer id) {
		
	}
}