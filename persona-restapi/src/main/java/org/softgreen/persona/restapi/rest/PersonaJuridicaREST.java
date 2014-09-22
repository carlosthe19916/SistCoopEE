package org.softgreen.persona.restapi.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.softgreen.persona.representation.idm.AccionistaRepresentation;
import org.softgreen.persona.representation.idm.PersonaJuridicaRepresentation;

@Path("/personas/juridicas")
public interface PersonaJuridicaREST {

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findById(@PathParam("id") Long id);

	@GET
	@Path("/buscar")
	@Produces({ "application/xml", "application/json" })
	public Response findByTipoNumeroDocumento(
			@QueryParam("tipoDocumento") String tipoDocumento,
			@QueryParam("numeroDocumento") String numeroDocumento);

	@GET
	@Produces({ "application/xml", "application/json" })
	public Response findAll(@QueryParam("filterText") String filterText,
			@QueryParam("offset") Integer offset,
			@QueryParam("limit") Integer limit);

	@GET
	@Path("/count")
	@Produces({ "application/xml", "application/json" })
	public Response countAll();

	@POST
	@Produces({ "application/xml", "application/json" })
	public Response create(
			PersonaJuridicaRepresentation personaJuridicaRepresentation);

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response update(@PathParam("id") Long id,
			PersonaJuridicaRepresentation personaJuridicaRepresentation);

	@DELETE
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response remove(@PathParam("id") Long id);

	/**
	 * ACCIONISTAS
	 */

	@GET
	@Path("/{id}/accionistas/{idAccionista}")
	@Produces({ "application/xml", "application/json" })
	public Response findAccionistaById(@PathParam("id") Long id,
			@PathParam("idAccionista") Long idAccionista);

	@GET
	@Path("/{id}/accionistas")
	@Produces({ "application/xml", "application/json" })
	public Response findAllAccionistas(@PathParam("id") Long id);

	@POST
	@Path("/{id}/accionistas")
	@Produces({ "application/xml", "application/json" })
	public Response addAccionista(@PathParam("id") Long id,
			AccionistaRepresentation accionistaRepresentation);

	@PUT
	@Path("/{id}/accionistas/{idAccionista}")
	@Produces({ "application/xml", "application/json" })
	public Response updateAccionista(@PathParam("id") Long id,
			@PathParam("idAccionista") Long idAccionista,
			AccionistaRepresentation accionistaRepresentation);

	@DELETE
	@Path("/{id}/accionistas/{idAccionista}")
	@Produces({ "application/xml", "application/json" })
	public Response removeAccionista(@PathParam("id") Long id,
			@PathParam("idAccionista") Long idAccionista);

}