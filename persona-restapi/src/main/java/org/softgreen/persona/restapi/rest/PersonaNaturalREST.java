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

import org.softgreen.persona.representation.idm.PersonaNaturalRepresentation;

@Path("/personas/naturales")
public interface PersonaNaturalREST {

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
	public Response listAll(@QueryParam("filterText") String filterText,
			@QueryParam("offset") Integer offset,
			@QueryParam("limit") Integer limit);

	@GET
	@Path("/count")
	@Produces({ "application/xml", "application/json" })
	public Response countAll();

	@POST
	@Produces({ "application/xml", "application/json" })
	public Response create(
			PersonaNaturalRepresentation personaNaturalRepresentation);

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response update(@PathParam("id") Long id,
			PersonaNaturalRepresentation personaNaturalRepresentation);

	@DELETE
	@Path("/{id}")
	public Response remove(@PathParam("id") Long id);

}