package org.softgreen.sistcoop.organizacion.restapi.resources;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;
import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.AgenciaProvider;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.AgenciaRepresentation;

@Path("/agencias")
@Stateless
public class AgenciaResource {

	@Inject
	protected AgenciaProvider agenciaProvider;

	@BadgerFish
	@GET
	@Path("/codigo/{codigo}")
	@Produces({ "application/xml", "application/json" })
	public AgenciaRepresentation getAgenciaByCodigo(@PathParam("codigo") String codigo) {
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaByCodigo(codigo);
		AgenciaRepresentation agenciaRepresentation = ModelToRepresentation.toRepresentation(agenciaModel);
		return agenciaRepresentation;
	}

}