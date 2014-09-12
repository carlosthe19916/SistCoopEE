package org.softgreen.persona.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softgreen.persona.dao.DAO;
import org.softgreen.persona.dao.QueryParameter;
import org.softgreen.persona.entity.PersonaNatural;
import org.softgreen.persona.entity.TipoDocumento;
import org.softgreen.persona.exception.IllegalResultException;
import org.softgreen.persona.exception.NonexistentEntityException;
import org.softgreen.persona.exception.PreexistingEntityException;
import org.softgreen.persona.exception.RollbackFailureException;
import org.softgreen.persona.service.PersonaNaturalService;

@Named
@Stateless
@Remote(PersonaNaturalService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PersonaNaturalServiceBean implements PersonaNaturalService {

	private static Logger LOGGER = LoggerFactory.getLogger(PersonaNaturalServiceBean.class);

	@Inject
	private DAO<Long, PersonaNatural> personaNaturalDAO;

	@Inject
	private Validator validator;

	@Override
	public PersonaNatural findById(Long id) {
		if (id == null)
			return null;
		PersonaNatural persona = personaNaturalDAO.find(id);
		if (persona != null) {
			TipoDocumento documento = persona.getTipoDocumento();
			Hibernate.initialize(documento);
		}
		return persona;
	}

	@Override
	public PersonaNatural find(String tipoDocumento, String numeroDocumento) {
		if (tipoDocumento == null || numeroDocumento == null)
			return null;

		numeroDocumento = numeroDocumento.trim();
		if (numeroDocumento.isEmpty())
			return null;

		PersonaNatural result = null;
		try {
			QueryParameter queryParameter = QueryParameter.with("tipoDocumento", tipoDocumento).and("numeroDocumento", numeroDocumento);
			List<PersonaNatural> list = personaNaturalDAO.findByNamedQuery(PersonaNatural.findByTipoAndNumeroDocumento, queryParameter.parameters());
			if (list.size() > 1)
				throw new IllegalResultException("Se encontró mas de una persona con idDocumento:" + tipoDocumento + " y numero de documento:" + numeroDocumento);
			else
				for (PersonaNatural personaNatural : list) {
					result = personaNatural;
				}
		} catch (IllegalResultException e) {
			LOGGER.error(e.getMessage(), e.getLocalizedMessage(), e.getCause());
		}
		return result;
	}

	@Override
	public List<PersonaNatural> findAll() {
		return findAll(null, null, null);
	}

	@Override
	public List<PersonaNatural> findAll(Integer offset, Integer limit) {
		return findAll(null, offset, limit);
	}

	@Override
	public List<PersonaNatural> findAll(String filterText) {
		return findAll(filterText, null, null);
	}

	@Override
	public List<PersonaNatural> findAll(String filterText, Integer offset, Integer limit) {
		List<PersonaNatural> result = null;

		if (filterText == null)
			filterText = "";
		if (offset == null) {
			offset = 0;
		}
		offset = Math.abs(offset);
		if (limit != null) {
			limit = Math.abs(limit);
		}

		Integer offSetInteger = offset.intValue();
		Integer limitInteger = (limit != null ? limit.intValue() : null);

		QueryParameter queryParameter = QueryParameter.with("filterText", '%' + filterText.toUpperCase() + '%');
		result = personaNaturalDAO.findByNamedQuery(PersonaNatural.findByFilterText, queryParameter.parameters(), offSetInteger, limitInteger);

		return result;
	}

	@Override
	public int count() {
		return personaNaturalDAO.count();
	}

	public Long create(PersonaNatural personanatural) throws PreexistingEntityException {
		Set<ConstraintViolation<PersonaNatural>> violations = validator.validate(personanatural);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
		TipoDocumento tipoDocumento = personanatural.getTipoDocumento();
		String numeroDocumento = personanatural.getNumeroDocumento();
		PersonaNatural obj = find(tipoDocumento.getAbreviatura(), numeroDocumento);
		if (obj == null)
			personaNaturalDAO.create(personanatural);
		else
			throw new PreexistingEntityException("La persona con el Tipo y Numero de documento ya existe");
		return personanatural.getId();
	}

	@Override
	public void update(Long idPersona, PersonaNatural persona) throws NonexistentEntityException, PreexistingEntityException {
		Set<ConstraintViolation<PersonaNatural>> violations = validator.validate(persona);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
		PersonaNatural personaNaturalFromDB = personaNaturalDAO.find(idPersona);
		if (personaNaturalFromDB == null)
			throw new NonexistentEntityException("La persona con id " + idPersona + " no fue encontrado");

		TipoDocumento tipoDocumento = persona.getTipoDocumento();
		PersonaNatural p = this.find(tipoDocumento.getAbreviatura(), persona.getNumeroDocumento());
		if (p != null)
			if (p.getId() != idPersona)
				throw new PreexistingEntityException("Tipo y numero de documento ya existente");

		persona.setId(idPersona);
		personaNaturalDAO.update(persona);
	}

	@Override
	public void delete(Long idPersonaNatural) throws NonexistentEntityException, RollbackFailureException {
		PersonaNatural personaNatural = personaNaturalDAO.find(idPersonaNatural);
		if (personaNatural != null)
			personaNaturalDAO.delete(personaNatural);
		else
			throw new NonexistentEntityException("Objeto no existente");
	}

}
