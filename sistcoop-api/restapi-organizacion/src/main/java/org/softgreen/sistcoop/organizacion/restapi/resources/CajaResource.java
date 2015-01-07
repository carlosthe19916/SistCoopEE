package org.softgreen.sistcoop.organizacion.restapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.CajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.DetalleHistorialRepresentation;
import org.softgreen.sistcoop.organizacion.managers.CajaManager;
import org.softgreen.sistcoop.organizacion.restapi.representation.BovedaList;
import org.softgreen.sistcoop.organizacion.restapi.representation.DetalleHistorialList;

@Path("/cajas")
@Stateless
public class CajaResource {

	@Inject
	protected CajaProvider cajaProvider;

	@Inject
	protected CajaManager cajaManager;

	@BadgerFish
	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public CajaRepresentation getCajaById(@PathParam("id") Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		CajaRepresentation representation = ModelToRepresentation.toRepresentation(model);
		return representation;
	}

	@GET
	@Path("/{id}/bovedas")
	@Produces({ "application/xml", "application/json" })
	public BovedaList getBovedasAsignadas(@PathParam("id") Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		List<BovedaCajaModel> bovedaCajaList = model.getBovedaCajas();
		List<BovedaRepresentation> list = new ArrayList<BovedaRepresentation>();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaList) {
			BovedaModel bovedaModel = bovedaCajaModel.getBoveda();
			BovedaRepresentation bovedaRepresentation = new BovedaRepresentation();
			bovedaRepresentation.setId(bovedaModel.getId());
			bovedaRepresentation.setDenominacion(bovedaModel.getDenominacion());
			bovedaRepresentation.setAbierto(bovedaModel.isAbierto());
			bovedaRepresentation.setEstadoMovimiento(bovedaModel.getEstadoMovimiento());
			bovedaRepresentation.setEstado(bovedaModel.getEstado());

			list.add(bovedaRepresentation);
		}
		return new BovedaList(list);
	}

	@GET
	@Path("/{id}/detalle")
	@Produces({ "application/xml", "application/json" })
	public DetalleHistorialList getDetalle(@PathParam("id") Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		HistorialModel historialModel = model.getHistorialActivo();
		if (historialModel != null) {
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
	public void update(@PathParam("id") Integer id, CajaRepresentation rep) {
		/*
		 * CajaModel model = cajaProvider.getCajaById(id);
		 * model.setDenominacion(rep.getDenominacion()); model.commit();
		 */
	}

	@POST
	@Path("/{id}/desactivar")
	@Produces({ "application/xml", "application/json" })
	public void desactivar(@PathParam("id") Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		if (model == null) {
			throw new NotFoundException("Caja not found.");
		}
		cajaManager.desactivarCaja(model);
	}

	@POST
	@Path("/{id}/abrir")
	@Produces({ "application/xml", "application/json" })
	public void abrir(@PathParam("id") Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		if (model == null) {
			throw new NotFoundException("Caja not found.");
		}
		cajaManager.abrir(model);
	}

	@POST
	@Path("/{id}/cerrar")
	@Produces({ "application/xml", "application/json" })
	public void cerrar(@PathParam("id") Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		if (model == null) {
			throw new NotFoundException("Caja not found.");
		}
		cajaManager.cerrar(model);
	}
}