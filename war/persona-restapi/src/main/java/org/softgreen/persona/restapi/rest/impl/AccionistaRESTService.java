package org.softgreen.persona.restapi.rest.impl;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

import org.softgreen.persona.model.AccionistaModel;
import org.softgreen.persona.provider.AccionistaProvider;
import org.softgreen.persona.restapi.rest.AccionistaREST;

public class AccionistaRESTService implements AccionistaREST {

	@EJB
	protected AccionistaProvider accionistaProvider;

	@Override
	public Response findById(Long id) {
		AccionistaModel accionistaModel = accionistaProvider.getAccionistaById(id);
		return Response.ok(accionistaModel).build();
	}

	@Override
	public Response remove(Long id) {
		AccionistaModel accionistaModel = accionistaProvider.getAccionistaById(id);
		boolean result = accionistaProvider.removeAccionista(accionistaModel);
		if(result)
			return Response.noContent().build();
		else 
			return Response.serverError().build();	
	}

}