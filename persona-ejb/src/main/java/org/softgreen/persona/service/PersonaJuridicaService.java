package org.softgreen.persona.service;

import java.util.List;

import javax.ejb.Remote;

import org.softgreen.persona.entity.PersonaJuridica;

@Remote
public interface PersonaJuridicaService extends AbstractService<Long, PersonaJuridica> {

	public PersonaJuridica find(String tipoDocumento, String numeroDocumento);

	public List<PersonaJuridica> findAll(Integer offset, Integer limit);

	public List<PersonaJuridica> findAll(String filterText);

	public List<PersonaJuridica> findAll(String filterText, Integer offset, Integer limit);
}
