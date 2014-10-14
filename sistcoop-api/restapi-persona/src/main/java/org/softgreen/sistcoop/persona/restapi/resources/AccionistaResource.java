package org.softgreen.sistcoop.persona.restapi.resources;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.softgreen.sistcoop.persona.client.models.AccionistaModel;
import org.softgreen.sistcoop.persona.client.models.AccionistaProvider;
import org.softgreen.sistcoop.persona.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.persona.client.representations.idm.AccionistaRepresentation;
import javax.inject.Named;

@Path("/accionistas")
public class AccionistaResource {

	@Inject
	protected AccionistaProvider accionistaProvider;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findById(Long id) {
		AccionistaModel model = accionistaProvider.getAccionistaById(id);
		AccionistaRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return Response.ok(rep).build();
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