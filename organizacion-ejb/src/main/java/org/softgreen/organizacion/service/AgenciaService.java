package org.softgreen.organizacion.service;

import java.util.Set;

import javax.ejb.Remote;

import org.softgreen.organizacion.entity.Agencia;
import org.softgreen.organizacion.entity.Boveda;
import org.softgreen.organizacion.entity.Caja;

@Remote
public interface AgenciaService extends AbstractService<Integer, Agencia> {

	public Agencia findByCodigo(String codigoAgencia);

	public Set<Caja> getCajas(String codigoAgencia);
	
	public Set<Boveda> getBovedas(String codigoAgencia);


}
