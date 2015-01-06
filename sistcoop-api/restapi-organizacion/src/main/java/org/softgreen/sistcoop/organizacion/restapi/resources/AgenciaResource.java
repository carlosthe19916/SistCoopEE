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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;
import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.AgenciaProvider;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaProvider;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.organizacion.client.representations.idm.AgenciaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.CajaRepresentation;
import org.softgreen.sistcoop.organizacion.managers.SucursalManager;
import org.softgreen.sistcoop.organizacion.restapi.config.Jsend;
import org.softgreen.sistcoop.organizacion.restapi.representation.BovedaList;
import org.softgreen.sistcoop.organizacion.restapi.representation.CajaList;

@Path("/agencias")
@Stateless
public class AgenciaResource {
	
	@Inject
	protected BovedaProvider bovedaProvider;

	@Inject
	protected BovedaCajaProvider bovedaCajaProvider;
	
	@Inject
	protected CajaProvider cajaProvider;

	@Inject
	protected AgenciaProvider agenciaProvider;

	@Inject
	protected SucursalManager sucursalManager;

	@Inject
	protected RepresentationToModel representationToModel;

	@Context
	protected UriInfo uriInfo;

	@BadgerFish
	@GET
	@Path("/codigo/{codigo}")
	@Produces({ "application/xml", "application/json" })
	public AgenciaRepresentation getAgenciaByCodigo(@PathParam("codigo") String codigo) {
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaByCodigo(codigo);
		AgenciaRepresentation agenciaRepresentation = ModelToRepresentation.toRepresentation(agenciaModel);
		return agenciaRepresentation;
	}

	@BadgerFish
	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public AgenciaRepresentation getAgenciaById(@PathParam("id") Integer id) {
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(id);
		AgenciaRepresentation agenciaRepresentation = ModelToRepresentation.toRepresentation(agenciaModel);
		return agenciaRepresentation;
	}

	@GET
	@Path("/{id}/bovedas")
	@Produces({ "application/xml", "application/json" })
	public BovedaList getBovedas(@PathParam("id") Integer id, @QueryParam("estado") Boolean estado) {
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(id);
		if (agenciaModel == null)
			throw new NotFoundException();

		List<BovedaModel> list;
		if (estado == null)
			list = agenciaModel.getBovedas();
		else
			list = agenciaModel.getBovedas(estado);
		List<BovedaRepresentation> result = new ArrayList<BovedaRepresentation>();
		for (BovedaModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return new BovedaList(result);
	}

	@GET
	@Path("/{id}/cajas")
	@Produces({ "application/xml", "application/json" })
	public CajaList getCajas(@PathParam("id") Integer id, @QueryParam("estado") Boolean estado) {
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(id);
		if (agenciaModel == null)
			throw new NotFoundException();

		List<CajaModel> list;
		if (estado == null)
			list = agenciaModel.getCajas();
		else
			list = agenciaModel.getCajas(estado);
		List<CajaRepresentation> result = new ArrayList<CajaRepresentation>();
		for (CajaModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return new CajaList(result);
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public void updateAgencia(@PathParam("id") Integer id, AgenciaRepresentation agenciaRepresentation) {
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(id);
		if (agenciaModel == null) {
			throw new NotFoundException("Agencia not found.");
		}
		agenciaModel.setAbreviatura(agenciaRepresentation.getAbreviatura());
		agenciaModel.setDenominacion(agenciaRepresentation.getDenominacion());
		agenciaModel.setUbigeo(agenciaRepresentation.getUbigeo());
		agenciaModel.commit();
	}

	@POST
	@Path("/{id}/desactivar")
	@Produces({ "application/xml", "application/json" })
	public void desactivar(@PathParam("id") Integer id) {
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(id);
		if (agenciaModel == null) {
			throw new NotFoundException("Agencia not found.");
		}
		sucursalManager.desactivarAgencia(agenciaModel);
	}

	@POST
	@Path("/{id}/bovedas")
	@Produces({ "application/xml", "application/json" })
	public Response addBoveda(@PathParam("id") Integer id, BovedaRepresentation bovedaRepresentation) {
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(id);
		if (agenciaModel == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		BovedaModel bovedaModel = representationToModel.createBoveda(agenciaModel, bovedaRepresentation, bovedaProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(bovedaModel.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(bovedaModel.getId())).build();
	}
	
	@POST
	@Path("/{id}/cajas")
	@Produces({ "application/xml", "application/json" })
	public Response addCaja(@PathParam("id") Integer id, CajaRepresentation cajaRepresentation) {
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(id);
		if (agenciaModel == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		CajaModel cajaModel = representationToModel.createCaja(agenciaModel, cajaRepresentation, bovedaProvider, cajaProvider, bovedaCajaProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(cajaModel.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(cajaModel.getId())).build();
	}

}