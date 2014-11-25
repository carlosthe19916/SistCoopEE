package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaProvider;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.AgenciaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.BovedaEntity;

@Named
@Stateless
@Local(BovedaProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaBovedaProvider implements BovedaProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public BovedaModel addBoveda(AgenciaModel agenciaModel, String moneda, String denominacion) {
		BovedaEntity bovedaEntity = new BovedaEntity();

		AgenciaEntity agenciaEntity = AgenciaAdapter.toSucursalEntity(agenciaModel, em);
		bovedaEntity.setAgencia(agenciaEntity);

		bovedaEntity.setDenominacion(denominacion);
		bovedaEntity.setMoneda(moneda);
		bovedaEntity.setAbierto(false);
		bovedaEntity.setEstadoMovimiento(false);
		bovedaEntity.setEstado(true);

		em.persist(bovedaEntity);
		return new BovedaAdapter(em, bovedaEntity);
	}

	@Override
	public boolean removeBoveda(BovedaModel BovedaModel) {
		BovedaEntity BovedaEntity = em.find(BovedaEntity.class, BovedaModel.getId());
		if (em.contains(BovedaEntity))
			em.remove(BovedaEntity);
		else
			em.remove(em.getReference(BovedaEntity.class, BovedaEntity.getId()));
		return true;
	}

	@Override
	public BovedaModel getBovedaById(Integer id) {
		BovedaEntity BovedaEntity = this.em.find(BovedaEntity.class, id);
		return BovedaEntity != null ? new BovedaAdapter(em, BovedaEntity) : null;
	}

}
