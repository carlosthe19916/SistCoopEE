package org.softgreen.organizacion.controller;

import java.util.HashSet;
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

import org.softgreen.dao.DAO;
import org.softgreen.exception.NonexistentEntityException;
import org.softgreen.exception.PreexistingEntityException;
import org.softgreen.exception.RollbackFailureException;
import org.softgreen.organizacion.entity.Boveda;
import org.softgreen.organizacion.service.ts.BovedaServiceTS;

@Named
@Stateless
@Remote(BovedaServiceTS.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BovedaServiceBeanTS implements BovedaServiceTS {

	@Inject
	private DAO<Integer, Boveda> bovedaDAO;

	@Inject
	private Validator validator;

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
		Boveda boveda = bovedaDAO.find(id);
		if (boveda != null) {
			Set<ConstraintViolation<Boveda>> violations = validator.validate(t);
			if (violations.isEmpty()) {
				t.setId(id);
				bovedaDAO.update(boveda);
			} else {
				throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
			}
		} else {
			throw new NonexistentEntityException("Boveda no existente, UPDATE no ejecutado");
		}
	}

	@Override
	public void delete(Integer id) throws NonexistentEntityException, RollbackFailureException {
		Boveda boveda = bovedaDAO.find(id);
		if (boveda != null) {
			bovedaDAO.delete(boveda);
		} else {
			throw new NonexistentEntityException("Boveda no existente, DELETE no ejecutado");
		}
	}

}
