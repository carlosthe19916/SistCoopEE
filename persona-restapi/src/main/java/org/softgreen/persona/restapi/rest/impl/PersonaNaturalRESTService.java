package org.softgreen.persona.restapi.rest.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.softgreen.persona.entity.PersonaNatural;
import org.softgreen.persona.entity.TipoDocumento;
import org.softgreen.persona.entity.type.TipoPersona;
import org.softgreen.persona.exception.NonexistentEntityException;
import org.softgreen.persona.exception.PreexistingEntityException;
import org.softgreen.persona.exception.RollbackFailureException;
import org.softgreen.persona.restapi.rest.Jsend;
import org.softgreen.persona.restapi.rest.PersonaNaturalREST;
import org.softgreen.persona.service.MaestroService;
import org.softgreen.persona.service.PersonaNaturalService;

public class PersonaNaturalRESTService implements PersonaNaturalREST {	

	@Inject
	private Validator validator;

	@EJB
	private PersonaNaturalService personaNaturalService;

	@EJB
	private MaestroService maestroService;

	@Override
	public Response getTipoDocumentoPersonaNatural() {
		Response response;
		List<TipoDocumento> list = maestroService.getTipoDocumento(TipoPersona.NATURAL);
		response = Response.status(Response.Status.OK).entity(list).build();
		return response;
	}

	@Override
	public Response findById(Long id) {
		PersonaNatural personaNatural = personaNaturalService.findById(id);
		Response response = Response.status(Response.Status.OK).entity(personaNatural).build();
		return response;
	}

	@Override
	public Response findByTipoNumeroDocumento(String tipoDocumento, String numeroDocumento) {
		PersonaNatural personaNatural = personaNaturalService.find(tipoDocumento, numeroDocumento);
		Response response = Response.status(Response.Status.OK).entity(personaNatural).build();
		return response;
	}

	@Override
	public Response listAll(String filterText, Integer offset, Integer limit) {
		List<PersonaNatural> list = personaNaturalService.findAll(filterText, offset, limit);
		Response response = Response.status(Response.Status.OK).entity(list).build();
		return response;
	}

	@Override
	public Response countAll() {
		int count = personaNaturalService.count();
		Response response = Response.status(Response.Status.OK).entity(count).build();
		return response;
	}

	@Override
	public Response update(Long id, PersonaNatural personaNatural) {
		Response response;
		try {
			Set<ConstraintViolation<PersonaNatural>> violations = validator.validate(personaNatural);
			if (!violations.isEmpty()) {
				throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
			}
			personaNatural.setId(null);
			personaNaturalService.update(id, personaNatural);
			response = Response.status(Response.Status.NO_CONTENT).build();
		} catch (ConstraintViolationException e) {
			Jsend jsend = Jsend.getErrorJSend("datos invalidos");
			for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
				jsend.addMessage(violation.getPropertyPath().toString() + " " + violation.getMessage());
			}
			response = Response.status(Response.Status.BAD_REQUEST).entity(jsend).build();
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

	@Override
	public Response create(PersonaNatural personaNatural) {
		Response response;
		try {
			Set<ConstraintViolation<PersonaNatural>> violations = validator.validate(personaNatural);
			if (!violations.isEmpty()) {
				throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
			}
			Long idPersona = personaNaturalService.create(personaNatural);	
			response = Response.status(Status.CREATED).entity(Jsend.getSuccessJSend(idPersona)).build();
		} catch (ConstraintViolationException e) {
			Jsend jsend = Jsend.getErrorJSend("datos invalidos");
			for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
				jsend.addMessage(violation.getPropertyPath().toString() + " " + violation.getMessage());
			}
			response = Response.status(Response.Status.BAD_REQUEST).entity(jsend).build();
		} catch (PreexistingEntityException e) {
			Jsend jsend = Jsend.getErrorJSend(e.getMessage());
			response = Response.status(Response.Status.CONFLICT).entity(jsend).build();
		} catch (RollbackFailureException e) {
			Jsend jsend = Jsend.getErrorJSend(e.getMessage());
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsend).build();
		}
		return response;
	}

	@Override
	public Response delete(Long id) {
		Response response;
		try {
			personaNaturalService.delete(id);
			response = Response.status(Response.Status.NO_CONTENT).build();
		} catch (NonexistentEntityException e) {
			Jsend jsend = Jsend.getErrorJSend(e.getMessage());
			response = Response.status(Response.Status.NOT_FOUND).entity(jsend).build();
		} catch (RollbackFailureException e) {
			Jsend jsend = Jsend.getErrorJSend(e.getMessage());
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsend).build();
		}
		return response;
	}

}
