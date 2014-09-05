package org.softgreen.persona.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
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
import org.softgreen.dao.DAO;
import org.softgreen.dao.QueryParameter;
import org.softgreen.exception.IllegalResultException;
import org.softgreen.exception.NonexistentEntityException;
import org.softgreen.exception.PreexistingEntityException;
import org.softgreen.exception.RollbackFailureException;
import org.softgreen.persona.entity.Accionista;
import org.softgreen.persona.entity.PersonaJuridica;
import org.softgreen.persona.entity.PersonaNatural;
import org.softgreen.persona.entity.TipoDocumento;
import org.softgreen.persona.service.PersonaJuridicaService;

@Named
@Stateless
@Remote(PersonaJuridicaService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PersonaJuridicaServiceBean implements PersonaJuridicaService {

	@Inject
	private DAO<Long, PersonaJuridica> personaJuridicaDAO;

	@Inject
	private Validator validator;

	@EJB
	private PersonaJuridicaService personaJuridicaServiceNT;

	private static Logger LOGGER = LoggerFactory.getLogger(PersonaJuridicaServiceBean.class);

	@Override
	public Long create(PersonaJuridica personaJuridica) throws PreexistingEntityException, RollbackFailureException {
		Set<ConstraintViolation<PersonaJuridica>> violations = validator.validate(personaJuridica);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}

		TipoDocumento tipoDocumento = personaJuridica.getTipoDocumento();
		String numeroDocumento = personaJuridica.getNumeroDocumento();
		PersonaJuridica obj = personaJuridicaServiceNT.find(tipoDocumento.getAbreviatura(), numeroDocumento);
		if (obj == null)
			personaJuridicaDAO.create(personaJuridica);
		else
			throw new PreexistingEntityException("La persona con el Tipo y Numero de documento ya existe");

		return personaJuridica.getId();
	}

	@Override
	public void update(Long idPersonaJuridica, PersonaJuridica personaJuridica) throws NonexistentEntityException, PreexistingEntityException, RollbackFailureException {
		Set<ConstraintViolation<PersonaJuridica>> violations = validator.validate(personaJuridica);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}

		TipoDocumento tipoDocumento = personaJuridica.getTipoDocumento();
		String numeroDocumento = personaJuridica.getNumeroDocumento();

		PersonaJuridica personaDB = personaJuridicaServiceNT.find(tipoDocumento.getAbreviatura(), numeroDocumento);
		PersonaJuridica personaById = personaJuridicaDAO.find(idPersonaJuridica);
		if (personaById == null)
			throw new PreexistingEntityException("Persona juridica no encontrada");

		personaJuridica.setId(idPersonaJuridica);
		if (personaDB != null) {
			if (personaById.getId().equals(personaDB.getId())) {
				personaJuridicaDAO.update(personaJuridica);
			} else {
				throw new PreexistingEntityException("El document ya existe");
			}
		} else {
			personaJuridicaDAO.update(personaJuridica);
		}
	}

	@Override
	public void delete(Long id) throws NonexistentEntityException, RollbackFailureException {
		throw new RollbackFailureException("Metodo no implementado");
	}

	@Override
	public PersonaJuridica findById(Long id) {
		if (id == null)
			return null;
		PersonaJuridica persona = personaJuridicaDAO.find(id);
		return persona;
	}

	@Override
	public PersonaJuridica find(String tipoDocumento, String numerodocumento) {
		if (tipoDocumento == null || numerodocumento == null)
			return null;
		if (numerodocumento.isEmpty() || numerodocumento.trim().isEmpty())
			return null;
		PersonaJuridica result = null;
		try {
			QueryParameter queryParameter = QueryParameter.with("tipoDocumento", tipoDocumento).and("numeroDocumento", numerodocumento);
			List<PersonaJuridica> list = personaJuridicaDAO.findByNamedQuery(PersonaJuridica.findByTipoAndNumeroDocumento, queryParameter.parameters());
			if (list.size() > 1)
				throw new IllegalResultException("Se encontró mas de una persona con idDocumento:" + tipoDocumento + " y numero de documento:" + numerodocumento);
			else
				for (PersonaJuridica personaJuridica : list) {
					result = personaJuridica;
				}
		} catch (IllegalResultException e) {
			LOGGER.error(e.getMessage(), e.getLocalizedMessage(), e.getCause());
		}
		return result;
	}

	@Override
	public List<PersonaJuridica> findAll() {
		return findAll(null, null, null);
	}

	@Override
	public List<PersonaJuridica> findAll(Integer offset, Integer limit) {
		return findAll(null, offset, limit);
	}

	@Override
	public List<PersonaJuridica> findAll(String filterText) {
		return findAll(filterText, null, null);
	}

	@Override
	public List<PersonaJuridica> findAll(String filterText, Integer offset, Integer limit) {
		List<PersonaJuridica> result = null;

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

		QueryParameter queryParameter = QueryParameter.with("filtertext", '%' + filterText.toUpperCase() + '%');
		result = personaJuridicaDAO.findByNamedQuery(PersonaJuridica.findByFilterText, queryParameter.parameters(), offSetInteger, limitInteger);
		if (result != null) {
			for (PersonaJuridica personaJuridica : result) {
				Set<Accionista> accionistas = personaJuridica.getAccionistas();
				PersonaNatural representante = personaJuridica.getRepresentanteLegal();
				TipoDocumento tipoDocumento = personaJuridica.getTipoDocumento();
				Hibernate.initialize(representante);
				Hibernate.initialize(tipoDocumento);
				for (Accionista accionista : accionistas) {
					PersonaNatural p = accionista.getPersonaNatural();
					TipoDocumento doc = p.getTipoDocumento();
					Hibernate.initialize(accionista);
					Hibernate.initialize(p);
					Hibernate.initialize(doc);
				}
			}
		}
		return result;
	}

	@Override
	public int count() {
		return personaJuridicaDAO.count();
	}
}
