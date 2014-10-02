package org.softgreen.sistcoop.persona.restapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
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
import javax.ws.rs.core.UriInfo;

import org.softgreen.sistcoop.persona.client.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalProvider;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.ejb.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.persona.ejb.models.util.RepresentationToModel;
import org.softgreen.sistcoop.persona.ejb.representations.idm.PersonaNaturalRepresentation;

@Path("/personas/naturales")
public class PersonaNaturalResource {

	@Inject
	protected PersonaNaturalProvider personaNaturalProvider;

	@Inject
	protected TipoDocumentoProvider tipoDocumentoProvider;

	@Context
	protected UriInfo uriInfo;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findById(@PathParam("id") Long id) {
		PersonaNaturalModel personaNaturalModel = personaNaturalProvider.getPersonaNaturalById(id);
		PersonaNaturalRepresentation rep = ModelToRepresentation.toRepresentation(personaNaturalModel);
		return Response.ok(rep).build();
	}

	@GET
	@Path("/buscar")
	@Produces({ "application/xml", "application/json" })
	public Response findByTipoNumeroDocumento(@QueryParam("tipoDocumento") String tipoDocumento, @QueryParam("numeroDocumento") String numeroDocumento) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(tipoDocumento);
		PersonaNaturalModel personaNaturalModel = personaNaturalProvider.getPersonaNaturalByTipoNumeroDoc(tipoDocumentoModel, numeroDocumento);
		PersonaNaturalRepresentation rep = ModelToRepresentation.toRepresentation(personaNaturalModel);
		return Response.ok(rep).build();
	}

	@GET
	@Produces({ "application/xml", "application/json" })
	public Response listAll(@QueryParam("filterText") String filterText, @QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit) {
		List<PersonaNaturalRepresentation> results = new ArrayList<PersonaNaturalRepresentation>();
		List<PersonaNaturalModel> userModels;
		userModels = personaNaturalProvider.getPersonasNaturales();
		for (PersonaNaturalModel personaNaturalModel : userModels) {
			results.add(ModelToRepresentation.toRepresentation(personaNaturalModel));
		}
		return Response.ok(results).build();
	}

	@GET
	@Path("/count")
	@Produces({ "application/xml", "application/json" })
	public Response countAll() {
		return null;
	}

	@POST
	@Produces({ "application/xml", "application/json" })
	public Response create(PersonaNaturalRepresentation personaNaturalRepresentation) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(personaNaturalRepresentation.getTipoDocumento());
		PersonaNaturalModel personaNaturalModel = RepresentationToModel.createPersonaNatural(personaNaturalRepresentation, tipoDocumentoModel, personaNaturalProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(personaNaturalModel.getId().toString()).build()).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response update(@PathParam("id") Long id, PersonaNaturalRepresentation personaNaturalRepresentation) {
		// TODO Auto-generated method stub
		return null;
	}

	@DELETE
	@Path("/{id}")
	public Response remove(@PathParam("id") Long id) {
		PersonaNaturalModel personaNaturalModel = personaNaturalProvider.getPersonaNaturalById(id);
		personaNaturalProvider.removePersonaNatural(personaNaturalModel);
		return Response.noContent().build();
	}

}