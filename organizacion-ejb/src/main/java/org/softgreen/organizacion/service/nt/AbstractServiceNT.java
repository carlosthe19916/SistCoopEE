package org.softgreen.organizacion.service.nt;

import java.util.List;

public interface AbstractServiceNT<K, T> {

	public T findById(K id);

	public List<T> findAll();

	public int count();

}
