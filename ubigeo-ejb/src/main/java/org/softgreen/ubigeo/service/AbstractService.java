package org.softgreen.ubigeo.service;

import java.util.List;

import org.softgreen.ubigeo.exception.NonexistentEntityException;
import org.softgreen.ubigeo.exception.PreexistingEntityException;
import org.softgreen.ubigeo.exception.RollbackFailureException;

public interface AbstractService<K, T> {

	public T findById(K id);

	public List<T> findAll();

	public int count();

	public K create(T t) throws PreexistingEntityException, RollbackFailureException;

	public void update(K id, T t) throws NonexistentEntityException, PreexistingEntityException, RollbackFailureException;

	public void delete(K id) throws NonexistentEntityException, RollbackFailureException;

}