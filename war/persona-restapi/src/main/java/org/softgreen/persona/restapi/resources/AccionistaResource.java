package org.softgreen.persona.restapi.resources;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.softgreen.sistcoop.persona.models.AccionistaModel;
import org.softgreen.sistcoop.persona.models.AccionistaProvider;

public class AccionistaResource {

	@EJB
	protected AccionistaProvider accionistaProvider;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findById(Long id) {
		AccionistaModel accionistaModel = accionistaProvider.getAccionistaById(id);
		return Response.ok(accionistaModel).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response remove(Long id) {
		AccionistaModel accionistaModel = accionistaProvider.getAccionistaById(id);
		boolean result = accionistaProvider.removeAccionista(accionistaModel);
		if (result)
			return Response.noContent().build();
		else
			return Response.serverError().build();
	}

}