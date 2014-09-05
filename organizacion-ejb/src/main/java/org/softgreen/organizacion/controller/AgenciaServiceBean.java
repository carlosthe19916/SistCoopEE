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
import org.softgreen.organizacion.dao.QueryParameter;
import org.softgreen.organizacion.entity.Agencia;
import org.softgreen.organizacion.entity.Boveda;
import org.softgreen.organizacion.entity.Caja;
import org.softgreen.organizacion.service.AgenciaService;

@Named
@Stateless
@Remote(AgenciaService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AgenciaServiceBean implements AgenciaService {

	@Inject
	private DAO<Integer, Agencia> agenciaDAO;

	@Inject
	private Validator validator;

	@Override
	public Agencia findById(Integer id) {
		return agenciaDAO.find(id);
	}

	@Override
	public List<Agencia> findAll() {
		return agenciaDAO.findAll();
	}

	@Override
	public int count() {
		return agenciaDAO.count();
	}

	@Override
	public Integer create(Agencia t) throws PreexistingEntityException, RollbackFailureException {
		Set<ConstraintViolation<Agencia>> violations = validator.validate(t);
		if (violations.isEmpty()) {
			agenciaDAO.create(t);
			return t.getId();
		} else {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
	}

	@Override
	public void update(Integer id, Agencia t) throws NonexistentEntityException, PreexistingEntityException, RollbackFailureException {
		Agencia agencia = agenciaDAO.find(id);
		if (agencia != null) {
			Set<ConstraintViolation<Agencia>> violations = validator.validate(t);
			if (violations.isEmpty()) {
				t.setId(id);
				agenciaDAO.update(agencia);
			} else {
				throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
			}
		} else {
			throw new NonexistentEntityException("Agencia no existente, UPDATE no ejecutado");
		}
	}

	@Override
	public void delete(Integer id) throws NonexistentEntityException, RollbackFailureException {
		Agencia agencia = agenciaDAO.find(id);
		if (agencia != null) {
			agenciaDAO.delete(agencia);
		} else {
			throw new NonexistentEntityException("Agencia no existente, DELETE no ejecutado");
		}
	}

	@Override
	public Agencia findByCodigo(String codigo) {
		QueryParameter queryParameter = QueryParameter.with("codigo", codigo);
		List<Agencia> list = agenciaDAO.findByNamedQuery(Agencia.findByCodigo, queryParameter.parameters());
		if (list.size() <= 1) {
			Agencia agencia = null;
			for (Agencia a : list) {
				agencia = a;
			}
			return agencia;
		} else {
			return null;
		}
	}

	@Override
	public Set<Caja> getCajas(String codigoAgencia) {
		QueryParameter queryParameter = QueryParameter.with("codigo", codigoAgencia);
		List<Agencia> list = agenciaDAO.findByNamedQuery(Agencia.findByCodigo, queryParameter.parameters());
		if (list.size() <= 1) {
			Agencia agencia = null;
			for (Agencia a : list) {
				agencia = a;
			}
			return agencia.getCajas();
		} else {
			return null;
		}
	}

	@Override
	public Set<Boveda> getBovedas(String codigoAgencia) {
		QueryParameter queryParameter = QueryParameter.with("codigo", codigoAgencia);
		List<Agencia> list = agenciaDAO.findByNamedQuery(Agencia.findByCodigo, queryParameter.parameters());
		if (list.size() <= 1) {
			Agencia agencia = null;
			for (Agencia a : list) {
				agencia = a;
			}
			return agencia.getBovedas();
		} else {
			return null;
		}
	}

}
