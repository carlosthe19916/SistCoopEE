package org.softgreen.tasas.rest;

import java.util.TreeSet;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.softgreen.ubigeo.controller.UbigeoController;
import org.softgreen.ubigeo.entity.SubDivision;

@Path("/ubigeo")
public class UbigeoREST {

	@Inject
	private UbigeoController ubigeoController;
/*
	@GET
	@Path("/departamentos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDepartamentos() {
		Response response;
		TreeSet<SubDivision> list = ubigeoController.getDepartamentos();

		Jsend jsend = Jsend.getSuccessJSend();
		jsend.setData(list);

		response = Response.status(Response.Status.OK).entity(jsend).build();
		return response;
	}

	@GET
	@Path("/provincias")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProvinciasByCodigo(@QueryParam("codigoDepartamento") String codigoDepartamento) {
		Response response;
		TreeSet<Provincia> list = ubigeoController.getProvincias(codigoDepartamento);

		Jsend jsend = Jsend.getSuccessJSend();
		jsend.setData(list);

		response = Response.status(Response.Status.OK).entity(jsend).build();
		return response;
	}

	@GET
	@Path("/distritos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDistritosByCodigo(@QueryParam("codigoDepartamento") String codigoDepartamento, @QueryParam("codigoProvincia") String codigoProvincia) {
		Response response;
		TreeSet<Distrito> list = ubigeoController.getDistritos(codigoDepartamento, codigoProvincia);

		Jsend jsend = Jsend.getSuccessJSend();
		jsend.setData(list);

		response = Response.status(Response.Status.OK).entity(jsend).build();
		return response;
	}
*/
}
