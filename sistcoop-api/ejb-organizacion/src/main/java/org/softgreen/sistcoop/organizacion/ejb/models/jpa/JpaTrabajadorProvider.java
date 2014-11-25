package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorProvider;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TrabajadorEntity;

@Named
@Stateless
@Local(TrabajadorProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaTrabajadorProvider implements TrabajadorProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public TrabajadorModel addTrabajador(AgenciaModel agenciaModel, String tipoDocumento, String numeroDocumento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeTrabajador(TrabajadorModel TrabajadorModel) {
		TrabajadorEntity TrabajadorEntity = em.find(TrabajadorEntity.class, TrabajadorModel.getId());
		if (em.contains(TrabajadorEntity))
			em.remove(TrabajadorEntity);
		else
			em.remove(em.getReference(TrabajadorEntity.class, TrabajadorEntity.getId()));
		return true;
	}

}
