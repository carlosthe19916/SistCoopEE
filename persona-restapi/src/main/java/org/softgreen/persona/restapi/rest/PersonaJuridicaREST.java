package org.softgreen.persona.restapi.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.softgreen.persona.entity.PersonaJuridica;

@Path("/personas/juridicas")
public interface PersonaJuridicaREST {

	@GET
	@Path("/tipoDocumentos")
	@Produces({ "application/xml", "application/json" })
	public Response getTipoDocumentoPersonaJuridica();

	@GET
	@Produces({ "application/xml", "application/json" })
	public Response findAll(@QueryParam("filterText") String filterText, @QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit);

	@GET
	@Path("/buscar")
	@Produces({ "application/xml", "application/json" })
	public Response findByTipoNumeroDocumento(@QueryParam("tipoDocumento") String tipoDocumento, @QueryParam("numeroDocumento") String numeroDocumento);

	@GET
	@Path("/count")
	@Produces({ "application/xml", "application/json" })
	public Response count();

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findById(@PathParam("id") Long id);

	@POST
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response create(PersonaJuridica persona);

	@PUT
	@Path("/{id}")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response update(@PathParam("id") Long id, PersonaJuridica persona);

}
