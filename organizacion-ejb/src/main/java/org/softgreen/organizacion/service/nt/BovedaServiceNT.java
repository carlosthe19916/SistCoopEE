package org.softgreen.organizacion.service.nt;

import java.util.List;

import javax.ejb.Remote;

import org.softgreen.organizacion.entity.Boveda;

@Remote
public interface BovedaServiceNT extends AbstractServiceNT<Integer, Boveda> {

	public List<Boveda> findAll(Integer idAgencia);
}
