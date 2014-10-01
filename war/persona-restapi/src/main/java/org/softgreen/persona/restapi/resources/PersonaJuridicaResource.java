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

import org.softgreen.persona.restapi.managers.PersonaJuridicaManager;
import org.softgreen.sistcoop.persona.models.AccionistaModel;
import org.softgreen.sistcoop.persona.models.AccionistaProvider;
import org.softgreen.sistcoop.persona.models.PersonaJuridicaModel;
import org.softgreen.sistcoop.persona.models.PersonaJuridicaProvider;
import org.softgreen.sistcoop.persona.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.models.PersonaNaturalProvider;
import org.softgreen.sistcoop.persona.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.persona.models.util.RepresentationToModel;
import org.softgreen.sistcoop.persona.representations.idm.AccionistaRepresentation;
import org.softgreen.sistcoop.persona.representations.idm.PersonaJuridicaRepresentation;

@Path("/personas/juridicas")
public class PersonaJuridicaResource {

	@EJB
	protected PersonaJuridicaManager personaJuridicaManager;

	@EJB(lookup = "java:global/persona-ejb/JpaPersonaJuridicaProvider!org.softgreen.sistcoop.persona.models.jpa.PersonaJuridicaProvider")
	protected PersonaJuridicaProvider personaJuridicaProvider;

	@EJB(lookup = "java:global/persona-ejb/JpaPersonaNaturalProvider!org.softgreen.sistcoop.persona.models.jpa.PersonaNaturalProvider")
	protected PersonaNaturalProvider personaNaturalProvider;

	@EJB(lookup = "java:global/persona-ejb/JpaTipoDocumentoProvider!org.softgreen.sistcoop.persona.models.jpa.TipoDocumentoProvider")
	protected TipoDocumentoProvider tipoDocumentoProvider;

	@EJB
	protected AccionistaProvider accionistaProvider;

	@Context
	protected UriInfo uriInfo;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findById(@PathParam("id") Long id) {
		PersonaJuridicaModel model = personaJuridicaProvider.getPersonaJuridicaById(id);
		PersonaJuridicaRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return Response.ok(rep).build();
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
		TipoDocumentoModel representanteTipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(personaJuridicaRepresentation.getTipoDocumentoRepresentanteLegal());
		PersonaNaturalModel representanteModel = personaNaturalProvider.getPersonaNaturalByTipoNumeroDoc(representanteTipoDocumentoModel, personaJuridicaRepresentation.getNumeroDocumentoRepresentanteLegal());

		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(personaJuridicaRepresentation.getTipoDocumento());
		PersonaJuridicaModel personaJuridicaModel = RepresentationToModel.createPersonaJuridica(personaJuridicaRepresentation, tipoDocumentoModel, representanteModel, personaJuridicaProvider);
		PersonaJuridicaRepresentation result = ModelToRepresentation.toRepresentation(personaJuridicaModel);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(result.getId().toString()).build()).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response update(@PathParam("id") Long id, PersonaJuridicaRepresentation personaJuridicaRepresentation) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
			return null;
		}
		personaJuridicaManager.updatePersonaJuridicaFromRep(personaJuridicaModel, personaJuridicaRepresentation);
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
		boolean removed = personaJuridicaProvider.removePersonaJuridica(personaJuridicaModel);
		if (removed) {
			return Response.noContent().build();
		} else {
			return Response.serverError().build();
		}
	}

	/**
	 * ACCIONISTA
	 */
	@GET
	@Path("/{id}/accionistas/{idAccionista}")
	@Produces({ "application/xml", "application/json" })
	public Response findAccionistaById(@PathParam("id") Long id, @PathParam("idAccionista") Long idAccionista) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		AccionistaModel accionistaModel = accionistaProvider.getAccionistaById(idAccionista);
		AccionistaRepresentation accionistaRepresentation = ModelToRepresentation.toRepresentation(accionistaModel);
		return Response.ok().entity(accionistaRepresentation).build();
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
		List<AccionistaRepresentation> result = new ArrayList<AccionistaRepresentation>();
		for (AccionistaModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return Response.ok().entity(result).build();
	}

	@POST
	@Path("/{id}/accionistas")
	@Produces({ "application/xml", "application/json" })
	public Response addAccionista(@PathParam("id") Long id, AccionistaRepresentation accionistaRepresentation) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(accionistaRepresentation.getTipoDocumento());
		PersonaNaturalModel personaNaturalModel = personaNaturalProvider.getPersonaNaturalByTipoNumeroDoc(tipoDocumentoModel, accionistaRepresentation.getNumeroDocumento());

		AccionistaModel accionistaModel = personaJuridicaModel.addAccionista(personaNaturalModel, accionistaRepresentation.getPorcentajeParticipacion());
		AccionistaRepresentation representation = ModelToRepresentation.toRepresentation(accionistaModel);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(representation.getId().toString()).build()).build();
	}

	@PUT
	@Path("/{id}/accionistas/{idAccionista}")
	@Produces({ "application/xml", "application/json" })
	public Response updateAccionista(@PathParam("id") Long id, @PathParam("idAccionista") Long idAccionista, AccionistaRepresentation accionistaRepresentation) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		AccionistaModel accionistaModel = accionistaProvider.getAccionistaById(idAccionista);
		personaJuridicaManager.updateAccionistaFromRep(accionistaModel, accionistaRepresentation);
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id}/accionistas/{idAccionista}")
	@Produces({ "application/xml", "application/json" })
	public Response removeAccionista(@PathParam("id") Long id, @PathParam("idAccionista") Long idAccionista) {
		AccionistaModel accionistaModel = accionistaProvider.getAccionistaById(idAccionista);
		boolean removed = accionistaProvider.removeAccionista(accionistaModel);
		if (removed) {
			return Response.noContent().build();
		} else {
			return Response.serverError().build();
		}
	}

}