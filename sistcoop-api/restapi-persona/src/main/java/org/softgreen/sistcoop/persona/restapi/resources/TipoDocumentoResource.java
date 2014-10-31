package org.softgreen.sistcoop.persona.restapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.softgreen.sistcoop.persona.clien.enums.TipoPersona;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.persona.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.persona.client.representations.idm.TipoDocumentoRepresentation;
import org.softgreen.sistcoop.persona.restapi.representation.TipoDocumentoList;

@Path("/tiposDocumento")
@Stateless
public class TipoDocumentoResource {

	@Inject
	protected TipoDocumentoProvider tipoDocumentoProvider;

	@Inject
	protected RepresentationToModel representationToModel;

	@Context
	protected UriInfo uriInfo;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public TipoDocumentoRepresentation findById(@PathParam("id") String id) {
		TipoDocumentoModel model = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(id);
		TipoDocumentoRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@GET
	@Produces({ "application/xml", "application/json" })
	public TipoDocumentoList findAll(@QueryParam("tipoPersona") String tipoPersona) {
		List<TipoDocumentoModel> list = null;
		if (tipoPersona != null) {
			TipoPersona personType = TipoPersona.valueOf(tipoPersona.toUpperCase());
			if (personType != null) {
				list = tipoDocumentoProvider.getTiposDocumento(personType);
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
		TipoDocumentoModel tipoDocumentoModel = representationToModel.createTipoDocumento(tipoDocumentoRepresentation, tipoDocumentoProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(tipoDocumentoModel.getAbreviatura()).build()).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public void update(@PathParam("id") String id, TipoDocumentoRepresentation tipoDocumentoRepresentation) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(id);
		tipoDocumentoModel.setDenominacion(tipoDocumentoRepresentation.getDenominacion());
		tipoDocumentoModel.setTipoPersona(TipoPersona.valueOf(tipoDocumentoRepresentation.getTipoPersona().toUpperCase()));
		tipoDocumentoRepresentation.setCantidadCaracteres(tipoDocumentoRepresentation.getCantidadCaracteres());
		tipoDocumentoModel.commit();
	}

	@DELETE
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public void delete(@PathParam("id") String id) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(id);
		boolean removed = tipoDocumentoProvider.removeTipoDocumento(tipoDocumentoModel);
		if (!removed)
			throw new InternalServerErrorException("No se pudo eliminar el elemento");
	}

}