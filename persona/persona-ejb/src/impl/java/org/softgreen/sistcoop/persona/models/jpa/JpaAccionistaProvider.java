package org.softgreen.sistcoop.persona.models.jpa;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.softgreen.sistcoop.persona.models.AccionistaModel;
import org.softgreen.sistcoop.persona.models.AccionistaProvider;
import org.softgreen.sistcoop.persona.models.PersonaJuridicaModel;
import org.softgreen.sistcoop.persona.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.models.jpa.entities.AccionistaEntity;

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

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
