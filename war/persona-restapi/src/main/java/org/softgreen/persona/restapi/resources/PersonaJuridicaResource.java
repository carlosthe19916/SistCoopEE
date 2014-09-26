package org.softgreen.persona.restapi.resources;

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
import javax.ws.rs.core.UriInfo;

import org.softgreen.sistcoop.persona.models.AccionistaModel;
import org.softgreen.sistcoop.persona.models.PersonaJuridicaModel;
import org.softgreen.sistcoop.persona.models.PersonaJuridicaProvider;
import org.softgreen.sistcoop.persona.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.persona.representations.idm.AccionistaRepresentation;
import org.softgreen.sistcoop.persona.representations.idm.PersonaJuridicaRepresentation;

@Path("/personas/juridicas")
public class PersonaJuridicaResource {

	@EJB
	protected PersonaJuridicaProvider personaJuridicaProvider;

	@EJB
	protected TipoDocumentoProvider tipoDocumentoProvider;

	@Context
	protected UriInfo uriInfo;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findById(@PathParam("id") Long id) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		PersonaJuridicaRepresentation personaJuridicaRepresentation = ModelToRepresentation.toRepresentation(personaJuridicaModel);
		return Response.ok(personaJuridicaRepresentation).build();
	}

	@GET
	@Path("/buscar")
	@Produces({ "application/xml", "application/json" })
	public Response findByTipoNumeroDocumento(@QueryParam("tipoDocumento") String tipoDocumento, @QueryParam("numeroDocumento") String numeroDocumento) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(tipoDocumento);
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaByTipoNumeroDoc(tipoDocumentoModel, numeroDocumento);
		PersonaJuridicaRepresentation personaJuridicaRepresentation = ModelToRepresentation.toRepresentation(personaJuridicaModel);
		return Response.ok(personaJuridicaRepresentation).build();
	}

	@GET
	@Produces({ "application/xml", "application/json" })
	public Response findAll(@QueryParam("filterText") String filterText, @QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit) {
		List<PersonaJuridicaModel> list = personaJuridicaProvider.searchForFilterText(filterText, offset, limit);
		List<PersonaJuridicaRepresentation> result = new ArrayList<PersonaJuridicaRepresentation>();
		for (PersonaJuridicaModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return Response.ok(result).build();
	}

	@GET
	@Path("/count")
	@Produces({ "application/xml", "application/json" })
	public Response countAll() {
		Integer count = personaJuridicaProvider.getPersonasJuridicasCount();
		return Response.ok(count).build();
	}

	@POST
	@Produces({ "application/xml", "application/json" })
	public Response create(PersonaJuridicaRepresentation personaJuridicaRepresentation) {
		// PersonaJuridicaModel personaJuridicaModel =
		// RepresentationToModel.createPersonaJuridica(personaJuridicaRepresentation);
		// PersonaJuridicaRepresentation result =
		// ModelToRepresentation.toRepresentation(personaJuridicaModel);
		// return
		// Response.created(uriInfo.getAbsolutePathBuilder().path(result.getId().toString()).build()).build();
		return null;
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response update(@PathParam("id") Long id, PersonaJuridicaRepresentation personaJuridicaRepresentation) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
			return null;
		}
		updatePersonaJuridicaFromRep(personaJuridicaModel, personaJuridicaRepresentation);

		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response remove(@PathParam("id") Long id) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		// boolean removed = new
		// PersonaJuridicaManager().removePersonaJuridica(personaJuridicaModel);
		// if (removed) {
		// return Response.noContent().build();
		// } else {
		// return Response.serverError().build();
		// }
		return null;
	}

	/**
	 * ACCIONISTA
	 */
	@GET
	@Path("/{id}/accionistas/{idAccionista}")
	@Produces({ "application/xml", "application/json" })
	public Response findAccionistaById(@PathParam("id") Long id, @PathParam("idAccionista") Long idAccionista) {
		/*
		 * PersonaJuridicaModel personaJuridicaModel =
		 * personaJuridicaProvider.getPersonaJuridicaById(id); if
		 * (personaJuridicaModel == null) { return
		 * Response.status(Response.Status.NOT_FOUND).build(); } AccionistaModel
		 * accionistaModel = personaJuridicaModel.getAccionista(idAccionista);
		 * return Response.ok().entity(accionistaModel).build();
		 */
		return null;
	}

	@GET
	@Path("/{id}/accionistas")
	@Produces({ "application/xml", "application/json" })
	public Response findAllAccionistas(@PathParam("id") Long id) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		List<AccionistaModel> list = personaJuridicaModel.getAccionistas();
		return Response.ok().entity(list).build();
	}

	@POST
	@Path("/{id}/accionistas")
	@Produces({ "application/xml", "application/json" })
	public Response addAccionista(@PathParam("id") Long id, AccionistaRepresentation accionistaRepresentation) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		// personaJuridicaModel.addAccionista(accionistaModel)
		// AccionistaModel accionistaModel = RepresentationToModel.
		// AccionistaModel accionistaModel =
		// personaJuridicaModel.addAccionista(accionistaModel);
		return null;
	}

	@PUT
	@Path("/{id}/accionistas/{idAccionista}")
	@Produces({ "application/xml", "application/json" })
	public Response updateAccionista(@PathParam("id") Long id, @PathParam("idAccionista") Long idAccionista, AccionistaRepresentation accionistaRepresentation) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return null;
	}

	@DELETE
	@Path("/{id}/accionistas/{idAccionista}")
	@Produces({ "application/xml", "application/json" })
	public Response removeAccionista(@PathParam("id") Long id, @PathParam("idAccionista") Long idAccionista) {
		// TODO Auto-generated method stub
		return null;
	}

	private void updatePersonaJuridicaFromRep(PersonaJuridicaModel personaJuridica, PersonaJuridicaRepresentation rep) {
		// personaJuridica.setActividadPrincipal();
		// personaJuridica.
	}

}