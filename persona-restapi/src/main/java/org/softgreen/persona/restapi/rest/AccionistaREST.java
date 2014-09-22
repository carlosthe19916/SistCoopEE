package org.softgreen.persona.restapi.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/accionistas")
public interface AccionistaREST {

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findById(@PathParam("id") Long id);

	@DELETE
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response remove(@PathParam("id") Long id);

}