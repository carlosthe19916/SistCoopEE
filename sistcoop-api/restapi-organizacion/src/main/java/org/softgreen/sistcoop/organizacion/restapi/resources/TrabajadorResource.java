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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;
import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.AgenciaProvider;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorProvider;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.organizacion.client.representations.idm.AgenciaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.CajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.TrabajadorRepresentation;
import org.softgreen.sistcoop.organizacion.managers.TrabajadorManager;
import org.softgreen.sistcoop.organizacion.restapi.config.Jsend;
import org.softgreen.sistcoop.organizacion.restapi.representation.CajaList;

@Path("/trabajadores")
@Stateless
public class TrabajadorResource {

	@Inject
	protected AgenciaProvider agenciaProvider;
	
	@Inject
	protected TrabajadorManager trabajadorManager;
	
	@Inject
	protected TrabajadorProvider trabajadorProvider;

	@Inject
	protected CajaProvider cajaProvider;
	
	@Inject
	protected RepresentationToModel representationToModel;

	@Context
	protected UriInfo uriInfo;

	@BadgerFish
	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public TrabajadorRepresentation findById(@PathParam("id") Integer id) {
		TrabajadorModel model = trabajadorProvider.getTrabajadorById(id);
		TrabajadorRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}	

	@GET
	@Path("/{id}/cajas")
	@Produces({ "application/xml", "application/json" })
	public CajaList getCajas(@PathParam("id") Integer id) {
		TrabajadorModel model = trabajadorProvider.getTrabajadorById(id);
		if (model == null)
			throw new NotFoundException();

		List<TrabajadorCajaModel> trabajadorCajaModels = model.getTrabajadorCajas();
		List<CajaRepresentation> result = new ArrayList<CajaRepresentation>();
		for (TrabajadorCajaModel trabajadorCajaModel : trabajadorCajaModels) {
			CajaModel cajaModel = trabajadorCajaModel.getCaja();
			result.add(ModelToRepresentation.toRepresentation(cajaModel));
		}
		return new CajaList(result);
	}
	
	@POST
	@Produces({ "application/xml", "application/json" })
	public Response create(TrabajadorRepresentation rep) {
		AgenciaRepresentation agenciaRepresentation = rep.getAgencia();
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(agenciaRepresentation.getId());
		TrabajadorModel model = representationToModel.createTrabajador(agenciaModel, rep, trabajadorProvider);		
		return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(model.getId())).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public void update(@PathParam("id") Integer id, TrabajadorRepresentation rep) {
		TrabajadorModel model = trabajadorProvider.getTrabajadorById(id);
		if (model == null)
			throw new NotFoundException();
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(rep.getAgencia().getId());		
		model.setUsuario(rep.getUsuario());
		model.setAgencia(agenciaModel);	
		model.commit();
	}

	@DELETE
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public void delete(@PathParam("id") Integer id) {
		TrabajadorModel model = trabajadorProvider.getTrabajadorById(id);
		boolean removed = trabajadorProvider.removeTrabajador(model);
		if (!removed)
			throw new InternalServerErrorException("No se pudo eliminar el elemento");
	}

	@POST
	@Path("/{id}/desactivar")
	@Produces({ "application/xml", "application/json" })
	public void desactivar(@PathParam("id") Integer id) {
		TrabajadorModel model = trabajadorProvider.getTrabajadorById(id);
		trabajadorManager.desactivarTrabajador(model);
	}
	
}