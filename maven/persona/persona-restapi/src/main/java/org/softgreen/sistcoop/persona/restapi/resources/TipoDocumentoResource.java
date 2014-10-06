package org.softgreen.sistcoop.persona.restapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.softgreen.sistcoop.persona.clien.enums.TipoPersona;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.persona.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.persona.client.representations.idm.TipoDocumentoRepresentation;
import org.softgreen.sistcoop.persona.restapi.representation.TipoDocumentoList;

@Path("/tiposDocumento")
public class TipoDocumentoResource {

	@Inject
	protected TipoDocumentoProvider tipoDocumentoProvider;

	@Context
	protected UriInfo uriInfo;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findById(@PathParam("id") String id) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(id);
		TipoDocumentoRepresentation tipoDocumentoRepresentation = ModelToRepresentation.toRepresentation(tipoDocumentoModel);
		return Response.ok().entity(tipoDocumentoRepresentation).build();
	}

	@GET
	@Produces({ "application/xml", "application/json" })
	public TipoDocumentoList findAll(@QueryParam("tipoPersona") String tipoPersona) {
		List<TipoDocumentoModel> list = null;
		if (tipoPersona != null) {
			TipoPersona personType = TipoPersona.lookup(tipoPersona);
			if (personType != null) {
				list = tipoDocumentoProvider.getTiposDocumento(TipoPersona.valueOf(tipoPersona));
			} else {
				list = tipoDocumentoProvider.getTiposDocumento();
			}
		} else {
			list = tipoDocumentoProvider.getTiposDocumento();
		}

		List<TipoDocumentoRepresentation> result = new ArrayList<TipoDocumentoRepresentation>();
		for (TipoDocumentoModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}

		return new TipoDocumentoList(result);
	}

	@POST
	@Produces({ "application/xml", "application/json" })
	public Response create(TipoDocumentoRepresentation tipoDocumentoRepresentation) {
		TipoDocumentoModel tipoDocumentoModel = RepresentationToModel.createTipoDocumento(tipoDocumentoRepresentation, tipoDocumentoProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(tipoDocumentoModel.getAbreviatura()).build()).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response update(@PathParam("id") String id, TipoDocumentoRepresentation tipoDocumentoRepresentation) {
		/*
		 * TipoDocumentoModel tipoDocumentoModel =
		 * tipoDocumentoProvider.getTipoDocumentoByAbreviatura(id);
		 * tipoDocumentoModel
		 * .setDenominacion(tipoDocumentoRepresentation.getDenominacion());
		 * tipoDocumentoModel
		 * .setTipoPersona(TipoPersona.valueOf(tipoDocumentoRepresentation
		 * .getTipoPersona().toUpperCase()));
		 * tipoDocumentoRepresentation.setCantidadCaracteres
		 * (tipoDocumentoRepresentation.getCantidadCaracteres());
		 * tipoDocumentoModel.commit(); return Response.noContent().build();
		 */
		return null;
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