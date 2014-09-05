package org.softgreen.persona.service;

import java.util.List;

import org.softgreen.exception.NonexistentEntityException;
import org.softgreen.exception.PreexistingEntityException;
import org.softgreen.exception.RollbackFailureException;

public interface AbstractService<K, T> {

	public T findById(K id);

	public List<T> findAll();

	public int count();

	public K create(T t) throws PreexistingEntityException, RollbackFailureException;

	public void update(K id, T t) throws NonexistentEntityException, PreexistingEntityException, RollbackFailureException;

	public void delete(K id) throws NonexistentEntityException, RollbackFailureException;

}
