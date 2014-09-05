package org.softgreen.organizacion.service.ts;

import javax.ejb.Remote;

import org.softgreen.organizacion.entity.Agencia;

@Remote
public interface AgenciaServiceTS extends AbstractServiceTS<Integer, Agencia> {

}
