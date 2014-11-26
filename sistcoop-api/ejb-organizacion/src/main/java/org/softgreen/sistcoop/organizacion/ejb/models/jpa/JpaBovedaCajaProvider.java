package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.math.BigDecimal;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.BovedaCajaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.BovedaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.CajaEntity;

@Named
@Stateless
@Local(BovedaCajaProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaBovedaCajaProvider implements BovedaCajaProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public BovedaCajaModel addBovedaCaja(BovedaModel bovedaModel, CajaModel cajaModel) {
		BovedaCajaEntity bovedaCajaEntity = new BovedaCajaEntity();

		BovedaEntity bovedaEntity = BovedaAdapter.toBovedaEntity(bovedaModel, em);
		CajaEntity cajaEntity = CajaAdapter.toCajaEntity(cajaModel, em);

		bovedaCajaEntity.setBoveda(bovedaEntity);
		bovedaCajaEntity.setCaja(cajaEntity);
		bovedaCajaEntity.setSaldo(BigDecimal.ZERO);

		em.persist(bovedaCajaEntity);
		return new BovedaCajaAdapter(em, bovedaCajaEntity);
	}

	@Override
	public boolean removeBovedaCaja(BovedaCajaModel BovedaCajaModel) {
		BovedaCajaEntity BovedaCajaEntity = em.find(BovedaCajaEntity.class, BovedaCajaModel.getId());
		if (em.contains(BovedaCajaEntity))
			em.remove(BovedaCajaEntity);
		else
			em.remove(em.getReference(BovedaCajaEntity.class, BovedaCajaEntity.getId()));
		return true;
	}

}
