package org.softgreen.persona.restapi.rest.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.softgreen.persona.entity.PersonaJuridica;
import org.softgreen.persona.entity.TipoDocumento;
import org.softgreen.persona.entity.type.TipoPersona;
import org.softgreen.persona.exception.NonexistentEntityException;
import org.softgreen.persona.exception.PreexistingEntityException;
import org.softgreen.persona.exception.RollbackFailureException;
import org.softgreen.persona.restapi.rest.Jsend;
import org.softgreen.persona.restapi.rest.PersonaJuridicaREST;
import org.softgreen.persona.service.MaestroService;
import org.softgreen.persona.service.PersonaJuridicaService;

public class PersonaJuridicaRESTService implements PersonaJuridicaREST {

	@EJB
	private PersonaJuridicaService personaJuridicaService;

	@EJB
	private MaestroService maestroService;

	@Override
	public Response getTipoDocumentoPersonaJuridica() {
		Response response;
		List<TipoDocumento> list = maestroService.getTipoDocumento(TipoPersona.JURIDICA);
		response = Response.status(Response.Status.OK).entity(list).build();
		return response;
	}

	@Override
	public Response findAll(String filterText, Integer offset, Integer limit) {
		List<PersonaJuridica> list = personaJuridicaService.findAll(filterText, offset, limit);
		Response response = Response.status(Response.Status.OK).entity(list).build();
		return response;
	}

	@Override
	public Response findByTipoNumeroDocumento(String idTipoDocumento, String numeroDocumento) {
		PersonaJuridica personaJuridica = personaJuridicaService.find(idTipoDocumento, numeroDocumento);
		Response response = Response.status(Response.Status.OK).entity(personaJuridica).build();
		return response;
	}

	@Override
	public Response count() {
		int count = personaJuridicaService.count();
		Response response = Response.status(Response.Status.OK).entity(count).build();
		return response;
	}

	@Override
	public Response findById(Long id) {
		PersonaJuridica personaJuridica = personaJuridicaService.findById(id);
		Response response = Response.status(Response.Status.OK).entity(personaJuridica).build();
		return response;
	}

	@Override
	public Response create(PersonaJuridica persona) {
		Response response;
		try {			
			Long idPersona = personaJuridicaService.create(persona);					
			response = Response.status(Status.CREATED).entity(Jsend.getSuccessJSend(idPersona)).build();		
		} catch (PreexistingEntityException e) {
			Jsend jsend = Jsend.getErrorJSend(e.getMessage());
			response = Response.status(Response.Status.CONFLICT).entity(jsend).build();
		} catch (RollbackFailureException e) {
			Jsend jsend = Jsend.getErrorJSend(e.getMessage());
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsend).build();
		} catch (EJBException e) {
			Jsend jsend = Jsend.getErrorJSend(e.getMessage());
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsend).build();
		}
		return response;
	}

	@Override
	public Response update(Long id, PersonaJuridica persona) {
		Response response;
		try {			
			personaJuridicaService.update(id, persona);
			response = Response.status(Response.Status.NO_CONTENT).build();
		} catch (NonexistentEntityException e) {
			Jsend jsend = Jsend.getErrorJSend(e.getMessage());
			response = Response.status(Response.Status.NOT_FOUND).entity(jsend).build();
		} catch (PreexistingEntityException e) {
			Jsend jsend = Jsend.getErrorJSend(e.getMessage());
			response = Response.status(Response.Status.CONFLICT).entity(jsend).build();
		} catch (RollbackFailureException e) {
			Jsend jsend = Jsend.getErrorJSend(e.getMessage());
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsend).build();
		}
		return response;
	}

}
