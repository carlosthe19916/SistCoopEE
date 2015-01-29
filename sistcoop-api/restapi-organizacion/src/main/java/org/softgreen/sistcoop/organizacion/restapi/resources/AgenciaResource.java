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

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.AgenciaProvider;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaProvider;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.organizacion.client.representations.idm.AgenciaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.CajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.TrabajadorRepresentation;
import org.softgreen.sistcoop.organizacion.managers.SucursalManager;
import org.softgreen.sistcoop.organizacion.restapi.config.Jsend;

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

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public AgenciaRepresentation getAgenciaById(@PathParam("id") Integer id) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		AgenciaRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@GET
	@Path("/codigo/{codigo}")
	@Produces({ "application/xml", "application/json" })
	public AgenciaRepresentation getAgenciaByCodigo(@PathParam("codigo") String codigo) {
		AgenciaModel model = agenciaProvider.getAgenciaByCodigo(codigo);
		AgenciaRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@GET
	@Path("/{id}/bovedas")
	@Produces({ "application/xml", "application/json" })
	public List<BovedaRepresentation> getBovedas(@PathParam("id") Integer id, @QueryParam("estado") Boolean estado) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		if (model == null)
			throw new NotFoundException();

		List<BovedaModel> list;
		if (estado == null)
			list = model.getBovedas();
		else
			list = model.getBovedas(estado);
		List<BovedaRepresentation> result = new ArrayList<BovedaRepresentation>();
		for (BovedaModel bovedaModel : list) {
			result.add(ModelToRepresentation.toRepresentation(bovedaModel));
		}
		return result;
	}

	@GET
	@Path("/{id}/cajas")
	@Produces({ "application/xml", "application/json" })
	public List<CajaRepresentation> getCajas(@PathParam("id") Integer id, @QueryParam("estado") Boolean estado) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		if (model == null)
			throw new NotFoundException();

		List<CajaModel> list;
		if (estado == null)
			list = model.getCajas();
		else
			list = model.getCajas(estado);
		List<CajaRepresentation> result = new ArrayList<CajaRepresentation>();
		for (CajaModel cajaModel : list) {
			result.add(ModelToRepresentation.toRepresentation(cajaModel));
		}
		return result;
	}

	@GET
	@Path("/{id}/trabajadores")
	@Produces({ "application/xml", "application/json" })
	public List<TrabajadorRepresentation> getTrabajadores(@PathParam("id") Integer id, @QueryParam("estado") Boolean estado, @QueryParam("filterText") String filterText, @QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		if (model == null)
			throw new NotFoundException();

		List<TrabajadorModel> list;
		if (estado == null) {
			if (limit == null) {
				limit = -1;
			}
			if (offset == null) {
				offset = -1;
			}
			list = model.getTrabajadores(filterText, limit, offset);
		} else {
			list = model.getTrabajadores(estado);
		}

		List<TrabajadorRepresentation> result = new ArrayList<TrabajadorRepresentation>();
		for (TrabajadorModel trabajadorModel : list) {
			result.add(ModelToRepresentation.toRepresentation(trabajadorModel));
		}
		return result;
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public void updateAgencia(@PathParam("id") Integer id, AgenciaRepresentation agenciaRepresentation) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		if (model == null) {
			throw new NotFoundException("Agencia not found.");
		}
		model.setAbreviatura(agenciaRepresentation.getAbreviatura());
		model.setDenominacion(agenciaRepresentation.getDenominacion());
		model.setUbigeo(agenciaRepresentation.getUbigeo());
		model.commit();
	}

	@POST
	@Path("/{id}/desactivar")
	@Produces({ "application/xml", "application/json" })
	public void desactivar(@PathParam("id") Integer id) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		if (model == null) {
			throw new NotFoundException("Agencia not found.");
		}
		sucursalManager.desactivarAgencia(model);
	}

	@POST
	@Path("/{id}/bovedas")
	@Produces({ "application/xml", "application/json" })
	public Response addBoveda(@PathParam("id") Integer id, BovedaRepresentation bovedaRepresentation) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		if (model == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		BovedaModel bovedaModel = representationToModel.createBoveda(model, bovedaRepresentation, bovedaProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(bovedaModel.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(bovedaModel.getId())).build();
	}

	@POST
	@Path("/{id}/cajas")
	@Produces({ "application/xml", "application/json" })
	public Response addCaja(@PathParam("id") Integer id, CajaRepresentation cajaRepresentation) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		if (model == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		CajaModel cajaModel = representationToModel.createCaja(model, cajaRepresentation, bovedaProvider, cajaProvider, bovedaCajaProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(cajaModel.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(cajaModel.getId())).build();
	}

}