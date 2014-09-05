package org.softgreen.organizacion.service.nt;

import java.util.Set;

import javax.ejb.Remote;

import org.softgreen.organizacion.entity.Agencia;
import org.softgreen.organizacion.entity.Caja;

@Remote
public interface AgenciaServiceNT extends AbstractServiceNT<Integer, Agencia> {

	public Agencia findByCodigo(String codigo);

	public Set<Caja> getCajasOfAgencia(Integer idAgencia);

}
