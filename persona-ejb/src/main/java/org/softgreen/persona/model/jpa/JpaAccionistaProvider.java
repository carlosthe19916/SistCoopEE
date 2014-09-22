package org.softgreen.persona.model.jpa;

import javax.persistence.EntityManager;

import org.softgreen.persona.model.AccionistaModel;
import org.softgreen.persona.model.jpa.entity.AccionistaEntity;
import org.softgreen.persona.provider.AccionistaProvider;

public class JpaAccionistaProvider implements AccionistaProvider {

	protected AccionistaModel accionistaModel;
	protected EntityManager em;

	public JpaAccionistaProvider(EntityManager em, AccionistaModel accionistaModel) {
		this.em = em;
		this.accionistaModel = accionistaModel;
	}

	@Override
	public AccionistaModel getAccionistaById(Long id) {
		AccionistaEntity accionistaEntity = em.find(AccionistaEntity.class, id);
		return new AccionistaAdapter(em, accionistaEntity);
	}

	@Override
	public AccionistaModel addAccionista(AccionistaModel accionistaModel) {
		AccionistaEntity accionistaEntity = AccionistaAdapter.toAccionistaEntity(accionistaModel, em);
		em.persist(accionistaEntity);
		return new AccionistaAdapter(em, accionistaEntity);
	}

	@Override
	public boolean removeAccionista(AccionistaModel accionistaModel) {
		AccionistaEntity accionistaEntity = AccionistaAdapter.toAccionistaEntity(accionistaModel, em);
		em.remove(accionistaEntity);
		return true;
	}

}
