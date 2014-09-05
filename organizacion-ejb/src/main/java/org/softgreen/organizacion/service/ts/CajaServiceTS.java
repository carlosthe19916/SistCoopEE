package org.softgreen.organizacion.service.ts;

import javax.ejb.Remote;

import org.softgreen.exception.RollbackFailureException;
import org.softgreen.organizacion.entity.Caja;

@Remote
public interface CajaServiceTS extends AbstractServiceTS<Integer, Caja> {

	public void desactivar(Integer idCaja) throws RollbackFailureException;

}
