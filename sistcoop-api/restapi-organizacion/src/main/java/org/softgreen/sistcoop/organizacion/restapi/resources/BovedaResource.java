package org.softgreen.sistcoop.organizacion.restapi.resources;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaProvider;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaRepresentation;

@Path("/bovedas")
@Stateless
public class BovedaResource {

	@Inject
	protected BovedaProvider bovedaProvider;
	
	@BadgerFish
	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public BovedaRepresentation getBovedaById(@PathParam("id") Integer id) {
		BovedaModel bovedaModel = bovedaProvider.getBovedaById(id);
		BovedaRepresentation bovedaRepresentation = ModelToRepresentation.toRepresentation(bovedaModel);
		return bovedaRepresentation;
	}
	
}