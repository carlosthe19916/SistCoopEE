package org.softgreen.persona.model.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.softgreen.persona.model.PersonaJuridicaModel;
import org.softgreen.persona.model.TipoDocumentoModel;
import org.softgreen.persona.model.jpa.entity.PersonaJuridicaEntity;
import org.softgreen.persona.provider.PersonaJuridicaProvider;

public class JpaPersonaJuridicaProvider implements PersonaJuridicaProvider {

	protected PersonaJuridicaModel personaJuridicaModel;
	protected EntityManager em;

	public JpaPersonaJuridicaProvider(EntityManager em, PersonaJuridicaModel personaJuridicaModel) {
		this.em = em;
		this.personaJuridicaModel = personaJuridicaModel;
	}

	@Override
	public PersonaJuridicaModel addPersonaJuridica(PersonaJuridicaModel personaJuridicaModel) {
		PersonaJuridicaEntity personaJuridicaEntity = PersonaJuridicaAdapter.toPersonaJuridicaEntity(personaJuridicaModel, em);
		em.persist(personaJuridicaEntity);
		return new PersonaJuridicaAdapter(em, personaJuridicaEntity);
	}

	@Override
	public boolean removePersonaJuridica(PersonaJuridicaModel personaJuridicaModel) {
		PersonaJuridicaEntity personaJuridicaEntity = PersonaJuridicaAdapter.toPersonaJuridicaEntity(personaJuridicaModel, em);
		em.remove(personaJuridicaEntity);
		return true;
	}

	@Override
	public PersonaJuridicaModel getPersonaJuridicaById(Long id) {
		PersonaJuridicaEntity personaJuridicaEntity = em.find(PersonaJuridicaEntity.class, id);
		return new PersonaJuridicaAdapter(em, personaJuridicaEntity);
	}

	@Override
	public PersonaJuridicaModel getPersonaJuridicaByTipoNumeroDoc(TipoDocumentoModel tipoDocumento, String numeroDocumento) {
		TypedQuery<PersonaJuridicaEntity> query = em.createNamedQuery(
				PersonaJuridicaEntity.findByTipoAndNumeroDocumento,
				PersonaJuridicaEntity.class);		
		query.setParameter("tipoDocumento", tipoDocumento.getAbreviatura());
		query.setParameter("numeroDocumento", numeroDocumento);
		List<PersonaJuridicaEntity> results = query.getResultList();
		if (results.size() == 0)
			return null;
		return new PersonaJuridicaAdapter(em, results.get(0));
	}

	@Override
	public List<PersonaJuridicaModel> getPersonasJuridicas() {
		return getPersonasJuridicas(-1, -1);
	}

	@Override
	public int getPersonasJuridicasCount() {
		Object count = em.createNamedQuery(PersonaJuridicaEntity.count).getSingleResult();
		return ((Number) count).intValue();
	}

	@Override
	public List<PersonaJuridicaModel> getPersonasJuridicas(int firstResult,
			int maxResults) {
		TypedQuery<PersonaJuridicaEntity> query = em.createNamedQuery(
				PersonaJuridicaEntity.findAll, PersonaJuridicaEntity.class);
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<PersonaJuridicaEntity> results = query.getResultList();
		List<PersonaJuridicaModel> users = new ArrayList<PersonaJuridicaModel>();
		for (PersonaJuridicaEntity entity : results)
			users.add(new PersonaJuridicaAdapter(em, entity));
		return users;
	}

	@Override
	public List<PersonaJuridicaModel> searchForNumeroDocumento(
			String numeroDocumento) {
		return searchForNumeroDocumento(numeroDocumento, -1, -1);
	}

	@Override
	public List<PersonaJuridicaModel> searchForNumeroDocumento(
			String numeroDocumento, int firstResult, int maxResults) {
		TypedQuery<PersonaJuridicaEntity> query = em.createNamedQuery(PersonaJuridicaEntity.findByNumeroDocumento, PersonaJuridicaEntity.class);
		query.setParameter("numeroDocumento", numeroDocumento);
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<PersonaJuridicaEntity> results = query.getResultList();
		List<PersonaJuridicaModel> users = new ArrayList<PersonaJuridicaModel>();
		for (PersonaJuridicaEntity entity : results)
			users.add(new PersonaJuridicaAdapter(em, entity));
		return users;
	}

	@Override
	public List<PersonaJuridicaModel> searchForFilterText(String filterText) {
		return searchForFilterText(filterText, -1, -1);
	}

	@Override
	public List<PersonaJuridicaModel> searchForFilterText(String filterText,
			int firstResult, int maxResults) {
		TypedQuery<PersonaJuridicaEntity> query = em.createNamedQuery(PersonaJuridicaEntity.findByFilterText, PersonaJuridicaEntity.class);
		query.setParameter("filtertext", filterText);
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<PersonaJuridicaEntity> results = query.getResultList();
		List<PersonaJuridicaModel> users = new ArrayList<PersonaJuridicaModel>();
		for (PersonaJuridicaEntity entity : results)
			users.add(new PersonaJuridicaAdapter(em, entity));
		return users;
	}

	
}
