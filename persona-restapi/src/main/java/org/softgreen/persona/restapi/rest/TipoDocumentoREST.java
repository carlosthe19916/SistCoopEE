package org.softgreen.persona.restapi.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/tiposDocumento")
public interface TipoDocumentoREST {

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findById(@PathParam("id") String id);

	@GET
	@Produces({ "application/xml", "application/json" })
	public Response findAll(@QueryParam("tipoPersona") String tipoPersona);

}
