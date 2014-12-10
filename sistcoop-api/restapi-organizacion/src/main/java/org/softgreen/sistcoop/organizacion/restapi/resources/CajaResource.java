package org.softgreen.sistcoop.organizacion.restapi.resources;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.softgreen.sistcoop.organizacion.client.models.CajaProvider;

@Path("/cajas")
@Stateless
public class CajaResource {

	@Inject
	protected CajaProvider cajaProvider;
	
}