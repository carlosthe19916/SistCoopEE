package org.softgreen.persona.restapi.resources;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.softgreen.sistcoop.persona.models.AccionistaModel;
import org.softgreen.sistcoop.persona.models.AccionistaProvider;
import org.softgreen.sistcoop.persona.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.persona.representations.idm.AccionistaRepresentation;

@Path("/accionistas")
public class AccionistaResource {

	@EJB(lookup = "java:global/persona-ejb/JpaAccionistaProvider!org.softgreen.sistcoop.persona.models.jpa.AccionistaProvider")
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