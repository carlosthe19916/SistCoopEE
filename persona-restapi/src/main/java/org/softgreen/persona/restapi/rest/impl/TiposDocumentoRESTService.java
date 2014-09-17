package org.softgreen.persona.restapi.rest.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

import org.softgreen.persona.entity.TipoDocumento;
import org.softgreen.persona.entity.type.TipoPersona;
import org.softgreen.persona.restapi.rest.JsonAPI;
import org.softgreen.persona.restapi.rest.TipoDocumentoREST;
import org.softgreen.persona.service.MaestroService;

public class TiposDocumentoRESTService implements TipoDocumentoREST {

	@EJB
	private MaestroService maestroService;
	
	@Override
	public Response findAll(TipoPersona tipoPersona) {
		List<TipoDocumento> list = maestroService.getTipoDocumento(tipoPersona);
		JsonAPI api = new JsonAPI();
		api.setData(list);
		Response response = Response.status(Response.Status.OK).entity(api).build();
		return response;
	}

	@Override
	public Response findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
