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
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.CajaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TrabajadorCajaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TrabajadorEntity;

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
		TrabajadorCajaEntity trabajadorCajaEntity = new TrabajadorCajaEntity();

		CajaEntity cajaEntity = CajaAdapter.toCajaEntity(cajaModel, em);
		TrabajadorEntity trabajadorEntity = TrabajadorAdapter.toTrabajadorEntity(trabajadorModel, em);

		trabajadorCajaEntity.setCaja(cajaEntity);
		trabajadorCajaEntity.setTrabajador(trabajadorEntity);

		em.persist(trabajadorCajaEntity);
		return new TrabajadorCajaAdapter(em, trabajadorCajaEntity);
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
