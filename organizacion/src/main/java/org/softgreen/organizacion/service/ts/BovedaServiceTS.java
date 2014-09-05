package org.softgreen.organizacion.service.ts;

import javax.ejb.Remote;

import org.softgreen.organizacion.entity.Boveda;

@Remote
public interface BovedaServiceTS extends AbstractServiceTS<Integer, Boveda> {

}
