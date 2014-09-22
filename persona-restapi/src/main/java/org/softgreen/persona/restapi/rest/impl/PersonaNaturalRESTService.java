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
import org.softgreen.persona.model.util.ModelToRepresentation;
import org.softgreen.persona.model.util.RepresentationToModel;
import org.softgreen.persona.provider.PersonaNaturalProvider;
import org.softgreen.persona.representation.idm.PersonaNaturalRepresentation;
import org.softgreen.persona.restapi.rest.PersonaNaturalREST;

public class PersonaNaturalRESTService implements PersonaNaturalREST {

	@EJB
	protected PersonaNaturalProvider personaNaturalProvider;
	protected static final Logger logger = Logger.getLogger(PersonaNaturalRESTService.class);

	protected PersonaNaturalModel personaNaturalModel;

	@Context
    protected UriInfo uriInfo;
	
	@Override
	public Response findById(Long id) {
		PersonaNaturalModel personaNaturalModel = personaNaturalProvider.getPersonaNaturalById(id);
		PersonaNaturalRepresentation rep = ModelToRepresentation.toRepresentation(personaNaturalModel);		
		return Response.ok(rep).build();				
	}

	@Override
	public Response findByTipoNumeroDocumento(String tipoDocumento,
			String numeroDocumento) {
		
		//PersonaNaturalModel personaNaturalModel = personaNaturalProvider.getPersonaNaturalByTipoNumeroDoc(tipoDocumento, numeroDocumento);
		PersonaNaturalRepresentation rep = ModelToRepresentation.toRepresentation(personaNaturalModel);		
		return Response.ok(rep).build();
	}

	@Override
	public Response listAll(String filterText, Integer offset, Integer limit) {
		List<PersonaNaturalRepresentation> results = new ArrayList<PersonaNaturalRepresentation>();
        List<PersonaNaturalModel> userModels;
        userModels = personaNaturalProvider.getPersonasNaturales();
        for (PersonaNaturalModel personaNaturalModel : userModels) {
        	results.add(ModelToRepresentation.toRepresentation(personaNaturalModel));
		}
        return Response.ok(results).build();
	}

	@Override
	public Response countAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response update(Long id,
			PersonaNaturalRepresentation personaNaturalRepresentation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response create(
			PersonaNaturalRepresentation personaNaturalRepresentation) {
		PersonaNaturalModel personaNaturalModel = RepresentationToModel.createPersonaNatural(personaNaturalRepresentation);		
		return Response.created(uriInfo.getAbsolutePathBuilder().path(personaNaturalModel.getId().toString()).build()).build();
	}

	@Override
	public Response remove(Long id) {
		//PersonaNaturalModel personaNaturalModel = personaNaturalProvider.removePersonaNatural(id);
		
		return Response.noContent().build();
	}

}