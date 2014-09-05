package org.softgreen.organizacion.controller;

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

import org.softgreen.exception.NonexistentEntityException;
import org.softgreen.exception.PreexistingEntityException;
import org.softgreen.exception.RollbackFailureException;
import org.softgreen.organizacion.dao.DAO;
import org.softgreen.organizacion.entity.Boveda;
import org.softgreen.organizacion.service.BovedaService;

@Named
@Stateless
@Remote(BovedaService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BovedaServiceBean implements BovedaService {

	@Inject
	private DAO<Integer, Boveda> bovedaDAO;

	@Inject
	private Validator validator;

	@Override
	public Boveda findById(Integer id) {
		return bovedaDAO.find(id);
	}

	@Override
	public List<Boveda> findAll() {
		return bovedaDAO.findAll();
	}

	@Override
	public int count() {
		return bovedaDAO.count();
	}

	@Override
	public Integer create(Boveda t) throws PreexistingEntityException, RollbackFailureException {
		Set<ConstraintViolation<Boveda>> violations = validator.validate(t);
		if (violations.isEmpty()) {
			bovedaDAO.create(t);
			return t.getId();
		} else {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
	}

	@Override
	public void update(Integer id, Boveda t) throws NonexistentEntityException, PreexistingEntityException, RollbackFailureException {
		Boveda agencia = bovedaDAO.find(id);
		if (agencia != null) {
			Set<ConstraintViolation<Boveda>> violations = validator.validate(t);
			if (violations.isEmpty()) {
				t.setId(id);
				bovedaDAO.update(agencia);
			} else {
				throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
			}
		} else {
			throw new NonexistentEntityException("Agencia no existente, UPDATE no ejecutado");
		}
	}

	@Override
	public void delete(Integer id) throws NonexistentEntityException, RollbackFailureException {
		Boveda agencia = bovedaDAO.find(id);
		if (agencia != null) {
			bovedaDAO.delete(agencia);
		} else {
			throw new NonexistentEntityException("Agencia no existente, DELETE no ejecutado");
		}
	}

}
