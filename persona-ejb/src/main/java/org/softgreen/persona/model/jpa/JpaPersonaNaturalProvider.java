package org.softgreen.persona.model.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.softgreen.persona.model.PersonaJuridicaModel;
import org.softgreen.persona.model.PersonaNaturalModel;
import org.softgreen.persona.model.TipoDocumentoModel;
import org.softgreen.persona.model.jpa.entity.PersonaNaturalEntity;
import org.softgreen.persona.provider.PersonaNaturalProvider;

@Named
@Stateless
@Remote(PersonaNaturalProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaPersonaNaturalProvider implements PersonaNaturalProvider {

	protected PersonaNaturalModel personaNaturalModel;
	protected EntityManager em;

	public JpaPersonaNaturalProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public PersonaNaturalModel addPersonaNatural(
			PersonaNaturalModel personaNaturalModel) {
		PersonaNaturalEntity personaNaturalEntity = PersonaNaturalAdapter
				.toPersonaNaturalEntity(personaNaturalModel, em);

		em.persist(personaNaturalEntity);
		return personaNaturalModel;
	}

	@Override
	public boolean removePersonaNatural(PersonaNaturalModel personaNatural) {
		PersonaNaturalEntity personaNaturalEntity = em.find(
				PersonaNaturalEntity.class, personaNatural.getId());
		if (personaNaturalEntity == null)
			return false;
		removePersonaNatural(personaNaturalEntity);
		return true;
	}

	public void removePersonaNatural(PersonaNaturalEntity personaNaturalEntity) {
		em.remove(personaNaturalEntity);
	}

	@Override
	public PersonaNaturalModel getPersonaNaturalById(Long id) {
		PersonaNaturalEntity personaNaturalEntity = this.em.find(
				PersonaNaturalEntity.class, id);
		return new PersonaNaturalAdapter(em, personaNaturalEntity);
	}

	@Override
	public PersonaNaturalModel getPersonaNaturalByTipoNumeroDoc(
			TipoDocumentoModel tipoDocumento, String numeroDocumento) {
		TypedQuery<PersonaNaturalEntity> query = em.createNamedQuery(
				PersonaNaturalEntity.findByTipoAndNumeroDocumento,
				PersonaNaturalEntity.class);
		query.setParameter("tipoDocumento", tipoDocumento);
		query.setParameter("numeroDocumento", numeroDocumento);
		List<PersonaNaturalEntity> results = query.getResultList();
		if (results.size() == 0)
			return null;
		return new PersonaNaturalAdapter(em, results.get(0));
	}

	@Override
	public List<PersonaNaturalModel> getPersonasNaturales() {
		return getPersonasNaturales(-1, -1);
	}

	@Override
	public int getPersonasNaturalesCount() {
		Object count = em.createNamedQuery(PersonaNaturalEntity.count)
				.getSingleResult();
		return ((Number) count).intValue();
	}

	@Override
	public List<PersonaNaturalModel> getPersonasNaturales(int firstResult,
			int maxResults) {
		TypedQuery<PersonaNaturalEntity> query = em.createNamedQuery(
				PersonaNaturalEntity.findAll, PersonaNaturalEntity.class);
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<PersonaNaturalEntity> results = query.getResultList();
		List<PersonaNaturalModel> users = new ArrayList<PersonaNaturalModel>();
		for (PersonaNaturalEntity entity : results)
			users.add(new PersonaNaturalAdapter(em, entity));
		return users;
	}

	@Override
	public List<PersonaNaturalModel> searchForNumeroDocumento(
			String numeroDocumento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonaNaturalModel> searchForNumeroDocumento(
			String numeroDocumento, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonaNaturalModel> searchForFilterText(String filterText) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonaNaturalModel> searchForFilterText(String filterText,
			int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}
}
