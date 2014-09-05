package org.softgreen.tasas.rest;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.softgreen.ubigeo.controller.TasaCambioController;

@Path("/tasasCambio")
public class TasaCambioREST {

	@Inject
	private TasaCambioController tasaCambioController;

	@GET
	public Response getTasaCambio(@QueryParam("codigoOrigen") String codigoOrigen, @QueryParam("codigoDestino") String codigoDestino) {
		Response response;

		BigDecimal tasa = tasaCambioController.getTasaCambio(codigoOrigen, codigoDestino);

		response = Response.status(Status.OK).entity(tasa).build();

		return response;
	}
}
