package org.softgreen.sistcoop.persona.restapi.resources;

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

import org.softgreen.sistcoop.persona.clien.enums.TipoEmpresa;
import org.softgreen.sistcoop.persona.client.models.AccionistaModel;
import org.softgreen.sistcoop.persona.client.models.AccionistaProvider;
import org.softgreen.sistcoop.persona.client.models.PersonaJuridicaModel;
import org.softgreen.sistcoop.persona.client.models.PersonaJuridicaProvider;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalProvider;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.persona.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.persona.client.representations.idm.AccionistaRepresentation;
import org.softgreen.sistcoop.persona.client.representations.idm.PersonaJuridicaRepresentation;
import org.softgreen.sistcoop.persona.restapi.config.Jsend;

@Path("/personas/juridicas")
@Stateless
public class PersonaJuridicaResource {

	@Inject
	protected PersonaJuridicaProvider personaJuridicaProvider;

	@Inject
	protected PersonaNaturalProvider personaNaturalProvider;

	@Inject
	protected TipoDocumentoProvider tipoDocumentoProvider;

	@Inject
	protected AccionistaProvider accionistaProvider;

	@Inject
	protected RepresentationToModel representationToModel;

	@Context
	protected UriInfo uriInfo;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public PersonaJuridicaRepresentation findById(@PathParam("id") Long id) {
		PersonaJuridicaModel model = personaJuridicaProvider.getPersonaJuridicaById(id);
		PersonaJuridicaRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@GET
	@Path("/buscar")
	@Produces({ "application/xml", "application/json" })
	public PersonaJuridicaRepresentation findByTipoNumeroDocumento(@QueryParam("tipoDocumento") String tipoDocumento, @QueryParam("numeroDocumento") String numeroDocumento) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(tipoDocumento);
		PersonaJuridicaModel model = personaJuridicaProvider.getPersonaJuridicaByTipoNumeroDoc(tipoDocumentoModel, numeroDocumento);
		PersonaJuridicaRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@GET
	@Produces({ "application/xml", "application/json" })
	public List<PersonaJuridicaRepresentation> findAll(@QueryParam("filterText") String filterText, @QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit) {
		filterText = (filterText == null ? "" : filterText);
		offset = (offset == null ? -1 : offset);
		limit = (limit == null ? -1 : limit);

		List<PersonaJuridicaModel> list = personaJuridicaProvider.searchForFilterText(filterText, offset, limit);
		List<PersonaJuridicaRepresentation> result = new ArrayList<PersonaJuridicaRepresentation>();
		for (PersonaJuridicaModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return result;
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
		TipoDocumentoModel representanteTipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(personaJuridicaRepresentation.getRepresentanteLegal().getTipoDocumento());
		PersonaNaturalModel representanteModel = personaNaturalProvider.getPersonaNaturalByTipoNumeroDoc(representanteTipoDocumentoModel, personaJuridicaRepresentation.getRepresentanteLegal().getNumeroDocumento());

		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(personaJuridicaRepresentation.getTipoDocumento());
		PersonaJuridicaModel personaJuridicaModel = representationToModel.createPersonaJuridica(personaJuridicaRepresentation, tipoDocumentoModel, representanteModel, personaJuridicaProvider);
		PersonaJuridicaRepresentation result = ModelToRepresentation.toRepresentation(personaJuridicaModel);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(result.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(result.getId())).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public void update(@PathParam("id") Long id, PersonaJuridicaRepresentation rep) {
		PersonaJuridicaModel model = personaJuridicaProvider.getPersonaJuridicaById(id);
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(rep.getTipoDocumento());

		model.setCodigoPais(rep.getCodigoPais());
		model.setTipoDocumento(tipoDocumentoModel);
		model.setNumeroDocumento(rep.getNumeroDocumento());
		model.setRazonSocial(rep.getRazonSocial());
		model.setFechaConstitucion(rep.getFechaConstitucion());
		model.setActividadPrincipal(rep.getActividadPrincipal());
		model.setNombreComercial(rep.getNombreComercial());
		model.setFinLucro(rep.isFinLucro());
		model.setTipoEmpresa(TipoEmpresa.valueOf(rep.getTipoEmpresa()));

		model.setUbigeo(rep.getUbigeo());
		model.setDireccion(rep.getDireccion());
		model.setReferencia(rep.getReferencia());
		model.setTelefono(rep.getTelefono());
		model.setCelular(rep.getCelular());
		model.setEmail(rep.getEmail());

		model.commit();
	}

	@DELETE
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public void remove(@PathParam("id") Long id) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		boolean removed = personaJuridicaProvider.removePersonaJuridica(personaJuridicaModel);
		if (!removed) {
			throw new InternalServerErrorException("No se pudo eliminar el elemento");
		}
	}

	/**
	 * ACCIONISTA
	 */
	@GET
	@Path("/{id}/accionistas/{idAccionista}")
	@Produces({ "application/xml", "application/json" })
	public AccionistaRepresentation findAccionistaById(@PathParam("id") Long id, @PathParam("idAccionista") Long idAccionista) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
			return null;
		}
		AccionistaModel accionistaModel = accionistaProvider.getAccionistaById(idAccionista);
		AccionistaRepresentation accionistaRepresentation = ModelToRepresentation.toRepresentation(accionistaModel);
		return accionistaRepresentation;
	}

	@GET
	@Path("/{id}/accionistas")
	@Produces({ "application/xml", "application/json" })
	public List<AccionistaRepresentation> findAllAccionistas(@PathParam("id") Long id) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		List<AccionistaModel> list = personaJuridicaModel.getAccionistas();
		List<AccionistaRepresentation> result = new ArrayList<AccionistaRepresentation>();
		for (AccionistaModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return result;
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
		return Response.created(uriInfo.getAbsolutePathBuilder().path(representation.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").build();
	}

	@PUT
	@Path("/{id}/accionistas/{idAccionista}")
	@Produces({ "application/xml", "application/json" })
	public void updateAccionista(@PathParam("id") Long id, @PathParam("idAccionista") Long idAccionista, AccionistaRepresentation accionistaRepresentation) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
			throw new NotFoundException("Sucursal not found.");
		}
		AccionistaModel accionistaModel = accionistaProvider.getAccionistaById(idAccionista);
		if (accionistaModel == null) {
			throw new NotFoundException("Accionista not found.");
		}
		accionistaModel.setPorcentajeParticipacion(accionistaRepresentation.getPorcentajeParticipacion());
		accionistaModel.commit();
	}

	@DELETE
	@Path("/{id}/accionistas/{idAccionista}")
	@Produces({ "application/xml", "application/json" })
	public void removeAccionista(@PathParam("id") Long id, @PathParam("idAccionista") Long idAccionista) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
			throw new NotFoundException("Sucursal not found.");
		}
		AccionistaModel accionistaModel = accionistaProvider.getAccionistaById(idAccionista);
		if (accionistaModel == null) {
			throw new NotFoundException("Accionista not found.");
		}
		boolean removed = accionistaProvider.removeAccionista(accionistaModel);
		if (!removed)
			throw new InternalServerErrorException("Internal server error.");
	}

}