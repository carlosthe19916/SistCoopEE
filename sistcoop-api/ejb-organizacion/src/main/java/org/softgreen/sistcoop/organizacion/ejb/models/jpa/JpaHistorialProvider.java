package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialProvider;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialEntity;

@Named
@Stateless
@Local(HistorialProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaHistorialProvider implements HistorialProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public HistorialModel addHistorial(CajaModel cajaModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HistorialModel addHistorial(BovedaModel bovedaModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeHistorial(HistorialModel HistorialModel) {
		HistorialEntity HistorialEntity = em.find(HistorialEntity.class, HistorialModel.getId());
		if (em.contains(HistorialEntity))
			em.remove(HistorialEntity);
		else
			em.remove(em.getReference(HistorialEntity.class, HistorialEntity.getId()));
		return true;
	}

}
