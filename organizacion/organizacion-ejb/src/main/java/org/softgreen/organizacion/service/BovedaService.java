package org.softgreen.organizacion.service;

import javax.ejb.Remote;

import org.softgreen.organizacion.entity.Boveda;

@Remote
public interface BovedaService extends AbstractService<Integer, Boveda> {

}
