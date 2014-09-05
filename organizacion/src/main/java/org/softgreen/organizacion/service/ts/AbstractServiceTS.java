package org.softgreen.organizacion.service.ts;

import org.softgreen.exception.NonexistentEntityException;
import org.softgreen.exception.PreexistingEntityException;
import org.softgreen.exception.RollbackFailureException;

public interface AbstractServiceTS<K, T> {

	public K create(T t) throws PreexistingEntityException,
			RollbackFailureException;

	public void update(K id, T t) throws NonexistentEntityException,
			PreexistingEntityException, RollbackFailureException;

	public void delete(K id) throws NonexistentEntityException,
			RollbackFailureException;

}
