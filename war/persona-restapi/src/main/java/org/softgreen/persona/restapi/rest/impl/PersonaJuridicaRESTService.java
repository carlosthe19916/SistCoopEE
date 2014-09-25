package org.softgreen.persona.restapi.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.logging.Logger;
import org.softgreen.persona.manager.PersonaJuridicaManager;
import org.softgreen.persona.model.AccionistaModel;
import org.softgreen.persona.model.PersonaJuridicaModel;
import org.softgreen.persona.model.TipoDocumentoModel;
import org.softgreen.persona.model.util.ModelToRepresentation;
import org.softgreen.persona.model.util.RepresentationToModel;
import org.softgreen.persona.provider.PersonaJuridicaProvider;
import org.softgreen.persona.provider.TipoDocumentoProvider;
import org.softgreen.persona.representation.idm.AccionistaRepresentation;
import org.softgreen.persona.representation.idm.PersonaJuridicaRepresentation;
import org.softgreen.persona.restapi.rest.PersonaJuridicaREST;

public class PersonaJuridicaRESTService implements PersonaJuridicaREST {

	@EJB
	protected PersonaJuridicaProvider personaJuridicaProvider;	
	
	@EJB
	protected TipoDocumentoProvider tipoDocumentoProvider;	
	
	@Context
    protected UriInfo uriInfo;

	protected static final Logger logger = Logger.getLogger(PersonaJuridicaRESTService.class);

	@Override
	public Response findById(Long id) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		PersonaJuridicaRepresentation personaJuridicaRepresentation = ModelToRepresentation.toRepresentation(personaJuridicaModel);
		return Response.ok(personaJuridicaRepresentation).build();
	}

	@Override
	public Response findByTipoNumeroDocumento(String tipoDocumento, String numeroDocumento) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(tipoDocumento);
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaByTipoNumeroDoc(tipoDocumentoModel, numeroDocumento);
		PersonaJuridicaRepresentation personaJuridicaRepresentation = ModelToRepresentation.toRepresentation(personaJuridicaModel);
		return Response.ok(personaJuridicaRepresentation).build();
	}

	@Override
	public Response findAll(String filterText, Integer offset, Integer limit) {
		List<PersonaJuridicaModel> list = personaJuridicaProvider.searchForFilterText(filterText, offset, limit);
		List<PersonaJuridicaRepresentation> result = new ArrayList<PersonaJuridicaRepresentation>();
		for (PersonaJuridicaModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return Response.ok(result).build();
	}

	@Override
	public Response countAll() {
		Integer count = personaJuridicaProvider.getPersonasJuridicasCount();
		return Response.ok(count).build();
	}

	@Override
	public Response create(
			PersonaJuridicaRepresentation personaJuridicaRepresentation) {
		//PersonaJuridicaModel personaJuridicaModel = RepresentationToModel.createPersonaJuridica(personaJuridicaRepresentation);
		//PersonaJuridicaRepresentation result = ModelToRepresentation.toRepresentation(personaJuridicaModel);
		//return Response.created(uriInfo.getAbsolutePathBuilder().path(result.getId().toString()).build()).build();
		return null;
	}

	@Override
	public Response update(Long id,
			PersonaJuridicaRepresentation personaJuridicaRepresentation) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
            return null;
        }
		updatePersonaJuridicaFromRep(personaJuridicaModel, personaJuridicaRepresentation);
		
		return Response.noContent().build();
	}

	@Override
	public Response remove(Long id) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
        }
		boolean removed = new PersonaJuridicaManager().removePersonaJuridica(personaJuridicaModel);
		if (removed) {
            return Response.noContent().build();
        } else {
        	return Response.serverError().build();
        }
	}

	/**
	 * ACCIONISTA*/
	@Override
	public Response findAccionistaById(Long id, Long idAccionista) {
		/*PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
        }
		AccionistaModel accionistaModel = personaJuridicaModel.getAccionista(idAccionista);
		return Response.ok().entity(accionistaModel).build();*/
		return null;
	}

	@Override
	public Response findAllAccionistas(Long id) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
        }
		List<AccionistaModel> list = personaJuridicaModel.getAccionistas();
		return Response.ok().entity(list).build();
	}

	@Override
	public Response addAccionista(Long id, AccionistaRepresentation accionistaRepresentation) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
        }
		//personaJuridicaModel.addAccionista(accionistaModel)
		//AccionistaModel accionistaModel = RepresentationToModel.
		//AccionistaModel accionistaModel = personaJuridicaModel.addAccionista(accionistaModel);
		return null;
	}

	@Override
	public Response updateAccionista(Long id, Long idAccionista, AccionistaRepresentation accionistaRepresentation) {
		PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.getPersonaJuridicaById(id);
		if (personaJuridicaModel == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
        }
		
		return null;
	}

	@Override
	public Response removeAccionista(Long id, Long idAccionista) {
		// TODO Auto-generated method stub
		return null;
	}

	
	private void updatePersonaJuridicaFromRep(PersonaJuridicaModel personaJuridica, PersonaJuridicaRepresentation rep) {
		//personaJuridica.setActividadPrincipal();
		//personaJuridica.
    }
	

}