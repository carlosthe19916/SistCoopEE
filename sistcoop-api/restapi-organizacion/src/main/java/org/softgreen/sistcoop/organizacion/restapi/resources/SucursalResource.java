package org.softgreen.sistcoop.organizacion.restapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
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
import org.softgreen.sistcoop.organizacion.client.models.SucursalModel;
import org.softgreen.sistcoop.organizacion.client.models.SucursalProvider;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.organizacion.client.representations.idm.AgenciaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.SucursalRepresentation;
import org.softgreen.sistcoop.organizacion.managers.SucursalManager;
import org.softgreen.sistcoop.organizacion.restapi.config.Jsend;
import org.softgreen.sistcoop.organizacion.restapi.representation.AgenciaList;
import org.softgreen.sistcoop.organizacion.restapi.representation.SucursalList;

@Path("/sucursales")
@Stateless
public class SucursalResource {

	@Inject
	protected SucursalProvider sucursalProvider;

	@Inject
	protected AgenciaProvider agenciaProvider;

	@Inject
	protected RepresentationToModel representationToModel;

	@Inject
	protected SucursalManager sucursalManager;
	
	@Context
	protected UriInfo uriInfo;

	@BadgerFish
	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public SucursalRepresentation findById(@PathParam("id") Integer id) {
		SucursalModel model = sucursalProvider.getSucursalById(id);
		SucursalRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@GET
	@Produces({ "application/xml", "application/json" })
	public SucursalList findAll(@QueryParam("estado") Boolean estado) {
		List<SucursalModel> list = null;
		if (estado == null) {
			list = sucursalProvider.getSucursales();
		} else {
			list = sucursalProvider.getSucursales(estado);
		}
		List<SucursalRepresentation> result = new ArrayList<SucursalRepresentation>();
		for (SucursalModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return new SucursalList(result);
	}

	@POST
	@Produces({ "application/xml", "application/json" })
	public Response create(SucursalRepresentation rep) {
		SucursalModel model = representationToModel.createSucursal(rep, sucursalProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(model.getId())).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public void update(@PathParam("id") Integer id, SucursalRepresentation rep) {
		SucursalModel model = sucursalProvider.getSucursalById(id);
		model.setAbreviatura(rep.getAbreviatura());
		model.setDenominacion(rep.getDenominacion());
		model.commit();
	}

	@DELETE
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public void delete(@PathParam("id") Integer id) {
		SucursalModel model = sucursalProvider.getSucursalById(id);
		boolean removed = sucursalProvider.removeSucursal(model);
		if (!removed)
			throw new InternalServerErrorException("No se pudo eliminar el elemento");
	}

	@POST
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public void desactivar(@PathParam("id") Integer id) {
		SucursalModel model = sucursalProvider.getSucursalById(id);
		boolean result = sucursalManager.desactivarSucursal(model);
		if(!result)
			throw new InternalServerErrorException("Internal server error.");
	}

	/**
	 * Agencias
	 * */

	@BadgerFish
	@GET
	@Path("/{id}/agencias/{idAgencia}")
	@Produces({ "application/xml", "application/json" })
	public AgenciaRepresentation getAgenciaById(@PathParam("id") Integer id, @PathParam("idAgencia") Integer idAgencia) {
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(id);
		if (sucursalModel == null) {
			return null;
		}
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);
		AgenciaRepresentation agenciaRepresentation = ModelToRepresentation.toRepresentation(agenciaModel);
		return agenciaRepresentation;
	}

	@GET
	@Path("/{id}/agencias")
	@Produces({ "application/xml", "application/json" })
	public AgenciaList getAgencias(@PathParam("id") Integer id, @QueryParam("estado") Boolean estado) {
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(id);
		List<AgenciaModel> list;
		if (estado == null)
			list = sucursalModel.getAgencias();
		else
			list = sucursalModel.getAgencias(estado);
		List<AgenciaRepresentation> result = new ArrayList<AgenciaRepresentation>();
		for (AgenciaModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return new AgenciaList(result);
	}

	@POST
	@Path("/{id}/agencias")
	@Produces({ "application/xml", "application/json" })
	public Response addAgencia(@PathParam("id") Integer id, AgenciaRepresentation agenciaRepresentation) {
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(id);
		if (sucursalModel == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		AgenciaModel agenciaModel = representationToModel.createAgencia(sucursalModel, agenciaRepresentation, agenciaProvider);
		AgenciaRepresentation representation = ModelToRepresentation.toRepresentation(agenciaModel);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(representation.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").build();
	}

	@PUT
	@Path("/{id}/agencias/{idAgencia}")
	@Produces({ "application/xml", "application/json" })
	public void updateAgencia(@PathParam("id") Integer id, @PathParam("idAgencia") Integer idAgencia, AgenciaRepresentation agenciaRepresentation) {
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(id);
		if (sucursalModel == null) {
			throw new NotFoundException("Sucursal not found.");
		}
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);
		if (agenciaModel == null) {
			throw new NotFoundException("Agencia not found.");
		}
		agenciaModel.setAbreviatura(agenciaRepresentation.getAbreviatura());
		agenciaModel.setDenominacion(agenciaRepresentation.getDenominacion());
		agenciaModel.setUbigeo(agenciaRepresentation.getUbigeo());
		agenciaModel.commit();
	}

	@DELETE
	@Path("/{id}/agencias/{idAgencia}")
	@Produces({ "application/xml", "application/json" })
	public void removeAgencia(@PathParam("id") Integer id, @PathParam("idAgencia") Integer idAgencia) {
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(id);
		if (sucursalModel == null) {
			throw new NotFoundException("Sucursal not found.");
		}
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);
		if (agenciaModel == null) {
			throw new NotFoundException("Agencia not found.");
		}
		boolean removed = sucursalProvider.removeSucursal(sucursalModel);
		if (!removed)
			throw new InternalServerErrorException("Internal server error.");
	}

	@POST
	@Path("/{id}/agencias/{idAgencia}")
	@Produces({ "application/xml", "application/json" })
	public void desactivarAgencia(@PathParam("id") Integer id, @PathParam("idAgencia") Integer idAgencia) {
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(id);
		if (sucursalModel == null) {
			throw new NotFoundException("Sucursal not found.");
		}
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);
		if (agenciaModel == null) {
			throw new NotFoundException("Agencia not found.");
		}
		boolean result = sucursalManager.desactivarAgencia(agenciaModel);
		if(!result)
			throw new InternalServerErrorException("Internal server error.");
	}

}