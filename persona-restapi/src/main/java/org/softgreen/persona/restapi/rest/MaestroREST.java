package org.softgreen.persona.restapi.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public interface MaestroREST {

	@GET
	@Path("/tipoPersonas")
	@Produces({ "application/xml", "application/json" })
	public Response getTipoPersonas();

	@GET
	@Path("/estadosCiviles")
	@Produces({ "application/xml", "application/json" })
	public Response getEstadosCiviles();

	@GET
	@Path("/sexos")
	@Produces({ "application/xml", "application/json" })
	public Response getSexos();

	@GET
	@Path("/tiposEmpresa")
	@Produces({ "application/xml", "application/json" })
	public Response getTiposEmpresa();

}