package org.softgreen.sistcoop.organizacion.restapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
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
import org.softgreen.sistcoop.organizacion.client.models.SucursalModel;
import org.softgreen.sistcoop.organizacion.client.models.SucursalProvider;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.organizacion.client.representations.idm.AgenciaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.SucursalRepresentation;
import org.softgreen.sistcoop.organizacion.managers.SucursalManager;
import org.softgreen.sistcoop.organizacion.restapi.config.Jsend;

@Path("/sucursales")
@Stateless
public class SucursalResource {

	@Inject
	protected SucursalProvider sucursalProvider;

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
	public SucursalRepresentation findById(@PathParam("id") Integer id) {
		SucursalModel model = sucursalProvider.getSucursalById(id);
		SucursalRepresentation rep = ModelToRepresentation.toRepresentation(model);				
		return rep;
	}

	@GET
	@Produces({ "application/xml", "application/json" })
	public List<SucursalRepresentation> findAll(@QueryParam("estado") Boolean estado, @QueryParam("filterText") String filterText, @QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset) {
		List<SucursalModel> list = null;
		if (estado == null) {
			if (limit == null) {
				limit = -1;
			}
			if (offset == null) {
				offset = -1;
			}
			list = sucursalProvider.getSucursales(filterText, limit, offset);
		} else {
			list = sucursalProvider.getSucursales(estado);
		}
		List<SucursalRepresentation> result = new ArrayList<SucursalRepresentation>();
		for (SucursalModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return result;
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
	@Path("/{id}/desactivar")
	@Produces({ "application/xml", "application/json" })
	public void desactivar(@PathParam("id") Integer id) {
		SucursalModel model = sucursalProvider.getSucursalById(id);
		sucursalManager.desactivarSucursal(model);
	}

	/**
	 * Agencias
	 * */
	@GET
	@Path("/{id}/agencias")
	@Produces({ "application/xml", "application/json" })
	public List<AgenciaRepresentation> getAgencias(@PathParam("id") Integer id, @QueryParam("estado") Boolean estado) {
		SucursalModel model = sucursalProvider.getSucursalById(id);
		List<AgenciaModel> list;
		if (estado == null)
			list = model.getAgencias();
		else
			list = model.getAgencias(estado);
		List<AgenciaRepresentation> result = new ArrayList<AgenciaRepresentation>();
		for (AgenciaModel agenciaModel : list) {
			result.add(ModelToRepresentation.toRepresentation(agenciaModel));
		}
		return result;
	}

	@POST
	@Path("/{id}/agencias")
	@Produces({ "application/xml", "application/json" })
	public Response addAgencia(@PathParam("id") Integer id, AgenciaRepresentation agenciaRepresentation) {
		SucursalModel model = sucursalProvider.getSucursalById(id);
		if (model == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		AgenciaModel agenciaModel = representationToModel.createAgencia(model, agenciaRepresentation, agenciaProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(agenciaModel.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(agenciaModel.getId())).build();
	}

}