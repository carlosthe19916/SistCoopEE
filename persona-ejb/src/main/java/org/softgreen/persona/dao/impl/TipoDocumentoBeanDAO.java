/**
This file is part of javaee-patterns.

javaee-patterns is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

javaee-patterns is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.opensource.org/licenses/gpl-2.0.php>.

 * Copyright (c) 22. June 2009 Adam Bien, blog.adam-bien.com
 * http://press.adam-bien.com
 */
package org.softgreen.persona.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.softgreen.persona.dao.DAO;
import org.softgreen.persona.entity.TipoDocumento;

/**
 * A minimalistic CRUD implementation. Usually provides the implementation of
 * search methods as well.
 * 
 * @author adam-bien.com
 */
@Stateless
@Local(DAO.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoDocumentoBeanDAO implements DAO<String, TipoDocumento> {

	@PersistenceContext
	private EntityManager em;

	@Inject
	protected Validator validator;

	public TipoDocumento create(TipoDocumento t) {
		Set<ConstraintViolation<TipoDocumento>> violations = validator.validate(t);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
		this.em.persist(t);
		return t;
	}

	public void delete(TipoDocumento t) {
		t = this.em.merge(t);
		this.em.remove(t);
	}

	public TipoDocumento find(String id) {
		return this.em.find(TipoDocumento.class, id);
	}

	public TipoDocumento update(TipoDocumento t) {
		Set<ConstraintViolation<TipoDocumento>> violations = validator.validate(t);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
		return this.em.merge(t);
	}

	public List<TipoDocumento> findAll() {
		List<TipoDocumento> list = null;
		CriteriaQuery cq = this.em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(TipoDocumento.class));
		list = this.em.createQuery(cq).getResultList();
		return list;
	}

	public List<TipoDocumento> findRange(int[] range) {
		javax.persistence.criteria.CriteriaQuery cq = this.em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(TipoDocumento.class));
		javax.persistence.Query q = this.em.createQuery(cq);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	public int count() {
		javax.persistence.criteria.CriteriaQuery cq = this.em.getCriteriaBuilder().createQuery();
		javax.persistence.criteria.Root<TipoDocumento> rt = cq.from(TipoDocumento.class);
		cq.select(this.em.getCriteriaBuilder().count(rt));
		javax.persistence.Query q = this.em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	public List<TipoDocumento> findByNamedQuery(String namedQueryName) {
		return this.em.createNamedQuery(namedQueryName).getResultList();
	}

	public List<TipoDocumento> findByNamedQuery(String namedQueryName, Map<String, Object> parameters) {
		return findByNamedQuery(namedQueryName, parameters, 0);
	}

	public List<TipoDocumento> findByNamedQuery(String queryName, int resultLimit) {
		return this.em.createNamedQuery(queryName).setMaxResults(resultLimit).getResultList();
	}

	public List<TipoDocumento> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		Set<Entry<String, Object>> rawParameters = parameters.entrySet();
		Query query = this.em.createNamedQuery(namedQueryName);
		if (resultLimit > 0)
			query.setMaxResults(resultLimit);
		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.getResultList();
	}

	public List<TipoDocumento> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, Integer offset, Integer limit) {
		Set<Entry<String, Object>> rawParameters = parameters.entrySet();
		Query query = this.em.createNamedQuery(namedQueryName);
		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		query.setFirstResult(offset != null ? offset : 0);
		if (limit != null)
			query.setMaxResults(limit);
		return query.getResultList();
	}

}
