package org.softgreen.organizacion.service.nt;

import javax.ejb.Remote;

import org.softgreen.organizacion.entity.Agencia;
import org.softgreen.organizacion.entity.Caja;

@Remote
public interface SessionServiceNT {

	public Caja getCajaOfSession();

	public Agencia getAgenciaOfSession();

}
