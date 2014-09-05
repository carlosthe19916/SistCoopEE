package org.softgreen.persona.service;

import java.util.List;

import javax.ejb.Remote;

import org.softgreen.persona.entity.PersonaNatural;

@Remote
public interface PersonaNaturalService extends AbstractService<Long, PersonaNatural> {

	public PersonaNatural find(String tipoDocumento, String numeroDocumento);

	public List<PersonaNatural> findAll(Integer offset, Integer limit);

	public List<PersonaNatural> findAll(String filterText);

	public List<PersonaNatural> findAll(String filterText, Integer offset, Integer limit);

}
