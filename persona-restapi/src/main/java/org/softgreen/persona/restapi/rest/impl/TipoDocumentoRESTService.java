package org.softgreen.persona.restapi.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.logging.Logger;
import org.softgreen.persona.model.PersonaNaturalModel;
import org.softgreen.persona.model.TipoDocumentoModel;
import org.softgreen.persona.provider.PersonaNaturalProvider;
import org.softgreen.persona.provider.TipoDocumentoProvider;
import org.softgreen.persona.representation.idm.PersonaNaturalRepresentation;
import org.softgreen.persona.representation.idm.TipoDocumentoRepresentation;
import org.softgreen.persona.restapi.rest.PersonaNaturalREST;
import org.softgreen.persona.restapi.rest.TipoDocumentoREST;

public class TipoDocumentoRESTService implements TipoDocumentoREST {

	@EJB
	protected TipoDocumentoProvider tipoDocumentoProvider;
	
	@Override
	public Response findAll(String tipoPersona) {
		List<TipoDocumentoModel> list = tipoDocumentoProvider.getTiposDocumento();
		List<TipoDocumentoRepresentation> result = new ArrayList<TipoDocumentoRepresentation>();
		for (TipoDocumentoModel model : list) {
			result
		}
		return null;
	}

	@Override
	public Response findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}