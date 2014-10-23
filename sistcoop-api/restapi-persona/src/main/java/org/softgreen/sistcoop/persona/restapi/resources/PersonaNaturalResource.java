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

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalProvider;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.persona.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.persona.client.representations.idm.PersonaNaturalRepresentation;

@Path("/personas/naturales")
public class PersonaNaturalResource {

	@Inject
	protected PersonaNaturalProvider personaNaturalProvider;

	@Inject
	protected TipoDocumentoProvider tipoDocumentoProvider;

	@Context
	protected UriInfo uriInfo;

	@BadgerFish
	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public PersonaNaturalRepresentation findById(@PathParam("id") Long id) {
		PersonaNaturalModel personaNaturalModel = personaNaturalProvider.getPersonaNaturalById(id);
		PersonaNaturalRepresentation rep = ModelToRepresentation.toRepresentation(personaNaturalModel);
		return rep;
		/*
		 * Movie movie = new Movie();
		 * movie.setName("Transformers: Dark of the Moon");
		 * movie.setDirector("Michael Bay"); movie.setYear(2011);
		 */

		// return movie;

	}

	@BadgerFish
	@GET
	@Path("/buscar")
	@Produces({ "application/xml", "application/json" })
	public PersonaNaturalRepresentation findByTipoNumeroDocumento(@QueryParam("tipoDocumento") String tipoDocumento, @QueryParam("numeroDocumento") String numeroDocumento) {
		if (tipoDocumento == null)
			return null;
		if (numeroDocumento == null)
			return null;

		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(tipoDocumento);
		PersonaNaturalModel personaNaturalModel = personaNaturalProvider.getPersonaNaturalByTipoNumeroDoc(tipoDocumentoModel, numeroDocumento);
		PersonaNaturalRepresentation rep = ModelToRepresentation.toRepresentation(personaNaturalModel);
		return rep;
	}

	@GET
	@Produces({ "application/xml", "application/json" })
	public Response listAll(@QueryParam("filterText") String filterText, @QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit) {
		List<PersonaNaturalRepresentation> results = new ArrayList<PersonaNaturalRepresentation>();
		List<PersonaNaturalModel> userModels;
		if (filterText == null) {
			if (offset == null || limit == null) {
				userModels = personaNaturalProvider.getPersonasNaturales();
			} else {
				userModels = personaNaturalProvider.getPersonasNaturales(offset, limit);
			}
		} else {
			if (offset == null || limit == null) {
				userModels = personaNaturalProvider.searchForFilterText(filterText);
			} else {
				userModels = personaNaturalProvider.searchForFilterText(filterText, offset, limit);
			}
		}
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