package org.softgreen.persona.model.jpa;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.softgreen.persona.model.AccionistaModel;
import org.softgreen.persona.model.PersonaJuridicaModel;
import org.softgreen.persona.model.PersonaNaturalModel;
import org.softgreen.persona.model.jpa.entity.AccionistaEntity;
import org.softgreen.persona.provider.AccionistaProvider;

public class JpaAccionistaProvider implements AccionistaProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public AccionistaModel getAccionistaById(Long id) {
		AccionistaEntity accionistaEntity = em.find(AccionistaEntity.class, id);
		return new AccionistaAdapter(em, accionistaEntity);
	}

	@Override
	public boolean removeAccionista(AccionistaModel accionistaModel) {
		AccionistaEntity accionistaEntity = AccionistaAdapter.toAccionistaEntity(accionistaModel, em);
		em.remove(accionistaEntity);
		return true;
	}

	@Override
	public AccionistaModel addAccionista(PersonaJuridicaModel pjModel,
			PersonaNaturalModel pnModel, BigDecimal porcentaje) {
		// TODO Auto-generated method stub
		return null;
	}

}
