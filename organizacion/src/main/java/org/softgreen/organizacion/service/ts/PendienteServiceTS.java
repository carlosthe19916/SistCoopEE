package org.softgreen.organizacion.service.ts;

import javax.ejb.Remote;

import org.softgreen.organizacion.entity.PendienteCaja;

@Remote
public interface PendienteServiceTS extends AbstractServiceTS<Long, PendienteCaja> {

}
