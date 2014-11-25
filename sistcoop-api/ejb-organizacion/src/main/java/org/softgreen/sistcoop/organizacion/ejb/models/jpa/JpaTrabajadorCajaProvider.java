package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TrabajadorCajaEntity;

@Named
@Stateless
@Local(TrabajadorCajaProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaTrabajadorCajaProvider implements TrabajadorCajaProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public TrabajadorCajaModel addTrabajadorCaja(CajaModel cajaModel, TrabajadorModel trabajadorModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeTrabajadorCaja(TrabajadorCajaModel TrabajadorCajaModel) {
		TrabajadorCajaEntity TrabajadorCajaEntity = em.find(TrabajadorCajaEntity.class, TrabajadorCajaModel.getId());
		if (em.contains(TrabajadorCajaEntity))
			em.remove(TrabajadorCajaEntity);
		else
			em.remove(em.getReference(TrabajadorCajaEntity.class, TrabajadorCajaEntity.getId()));
		return true;
	}

}
