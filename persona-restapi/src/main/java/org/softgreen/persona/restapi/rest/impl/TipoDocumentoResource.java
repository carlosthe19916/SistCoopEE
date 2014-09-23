package org.softgreen.persona.restapi.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.links.AddLinks;
import org.jboss.resteasy.links.LinkResource;
import org.softgreen.persona.model.TipoDocumentoModel;
import org.softgreen.persona.model.type.TipoPersona;
import org.softgreen.persona.model.util.ModelToRepresentation;
import org.softgreen.persona.model.util.RepresentationToModel;
import org.softgreen.persona.provider.TipoDocumentoProvider;
import org.softgreen.persona.representation.idm.TipoDocumentoRepresentation;

@Path("/tiposDocumento")
public class TipoDocumentoResource {

	@EJB
	protected TipoDocumentoProvider tipoDocumentoProvider;

	@Context
	protected UriInfo uriInfo;

	@AddLinks
	@LinkResource
	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public TipoDocumentoRepresentation findById(@PathParam("id") String id) {
		TipoDocumentoModel model = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(id);
		TipoDocumentoRepresentation rep = ModelToRepresentation.toRepresentation(model);
		//return Response.ok().entity(rep).build();
		return rep;
	}

	@GET
	@Produces({ "application/xml", "application/json" })
	public Response findAll(@QueryParam("tipoPersona") String tipoPersona) {
		List<TipoDocumentoModel> list = null;
		TipoPersona type = null;
		if (tipoPersona != null) {			
			try{
				 type = TipoPersona.lookup(tipoPersona);
			} catch (RuntimeException e){
				return Response.status(Status.BAD_REQUEST).build();
			}						
		} 
		if (type != null) {
			list = tipoDocumentoProvider.getTiposDocumento(type);
		} else {
			list = tipoDocumentoProvider.getTiposDocumento();
		}

		List<TipoDocumentoRepresentation> result = new ArrayList<TipoDocumentoRepresentation>();
		for (TipoDocumentoModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return Response.ok().entity(result).build();
	}

	@POST
	@Produces({ "application/xml", "application/json" })
	public Response create(TipoDocumentoRepresentation tipoDocumentoRepresentation) {
		TipoDocumentoModel tipoDocumentoModel = RepresentationToModel.createTipoDocumento(tipoDocumentoRepresentation, tipoDocumentoProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(tipoDocumentoModel.getAbreviatura()).build()).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response update(@PathParam("id") String id, TipoDocumentoRepresentation tipoDocumentoRepresentation) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(id);
		tipoDocumentoModel.setDenominacion(tipoDocumentoRepresentation.getDenominacion());
		tipoDocumentoModel.setTipoPersona(TipoPersona.valueOf(tipoDocumentoRepresentation.getTipoPersona().toUpperCase()));
		tipoDocumentoRepresentation.setCantidadCaracteres(tipoDocumentoRepresentation.getCantidadCaracteres());
		tipoDocumentoModel.commit();
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response delete(@PathParam("id") String id) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(id);
		if (tipoDocumentoModel != null) {
			boolean removed = tipoDocumentoProvider.removeTipoDocumento(tipoDocumentoModel);
			if (removed)
				return Response.noContent().build();
			else
				return Response.serverError().build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
