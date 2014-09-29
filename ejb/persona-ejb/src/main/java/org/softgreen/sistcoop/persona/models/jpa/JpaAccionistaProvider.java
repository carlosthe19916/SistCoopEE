package org.softgreen.sistcoop.persona.models.jpa;

import java.math.BigDecimal;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.softgreen.sistcoop.persona.models.AccionistaModel;
import org.softgreen.sistcoop.persona.models.AccionistaProvider;
import org.softgreen.sistcoop.persona.models.PersonaJuridicaModel;
import org.softgreen.sistcoop.persona.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.models.jpa.entities.AccionistaEntity;
import org.softgreen.sistcoop.persona.models.jpa.entities.PersonaJuridicaEntity;
import org.softgreen.sistcoop.persona.models.jpa.entities.PersonaNaturalEntity;

@Named
@Stateless
@Remote(AccionistaProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaAccionistaProvider implements AccionistaProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public AccionistaModel addAccionista(PersonaJuridicaModel pjModel, PersonaNaturalModel pnModel, BigDecimal porcentaje) {
		PersonaJuridicaEntity personaJuridicaEntity = PersonaJuridicaAdapter.toPersonaJuridicaEntity(pjModel, em);
		PersonaNaturalEntity personaNaturalEntity = PersonaNaturalAdapter.toPersonaNaturalEntity(pnModel, em);
		AccionistaEntity accionistaEntity = new AccionistaEntity();
		accionistaEntity.setPersonaNatural(personaNaturalEntity);
		accionistaEntity.setPersonaJuridica(personaJuridicaEntity);
		accionistaEntity.setPorcentajeParticipacion(porcentaje);
		em.persist(accionistaEntity);
		return new AccionistaAdapter(em, accionistaEntity);
	}

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
	public void close() {
		// TODO Auto-generated method stub

	}

}
