package org.softgreen.sistcoop.organizacion.restapi.exceptions;

import javax.ejb.EJBException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EJBExceptionMapper implements ExceptionMapper<javax.ejb.EJBException> {

	@Override
	public Response toResponse(EJBException e) {	
		return Response.status(500).entity(e.getMessage()).build();
	}

}
