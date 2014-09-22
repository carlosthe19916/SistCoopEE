package org.softgreen.persona.restapi.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.softgreen.persona.representation.idm.TipoDocumentoRepresentation;

@Path("/tiposDocumento")
public interface TipoDocumentoREST {

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findById(@PathParam("id") String id);

	@GET
	@Produces({ "application/xml", "application/json" })
	public Response findAll(@QueryParam("tipoPersona") String tipoPersona);

	@POST
	@Produces({ "application/xml", "application/json" })
	public Response create(TipoDocumentoRepresentation tipoDocumentoRepresentation);

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response update(@PathParam("id") String id, TipoDocumentoRepresentation tipoDocumentoRepresentation);

	@DELETE
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response delete(@PathParam("id") String id);
}
