package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaProvider;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.AgenciaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.BovedaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.CajaEntity;

@Named
@Stateless
@Local(CajaProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaCajaProvider implements CajaProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public CajaModel addCaja(AgenciaModel agenciaModel, String denominacion) {
		CajaEntity cajaEntity = new CajaEntity();

		AgenciaEntity agenciaEntity = AgenciaAdapter.toSucursalEntity(agenciaModel, em);
		cajaEntity.setAgencia(agenciaEntity);

		cajaEntity.setDenominacion(denominacion);		
		cajaEntity.setAbierto(false);
		cajaEntity.setEstadoMovimiento(false);
		cajaEntity.setEstado(true);

		em.persist(cajaEntity);
		return new CajaAdapter(em, cajaEntity);
	}

	@Override
	public boolean removeCaja(CajaModel CajaModel) {
		CajaEntity CajaEntity = em.find(CajaEntity.class, CajaModel.getId());
		if (em.contains(CajaEntity))
			em.remove(CajaEntity);
		else
			em.remove(em.getReference(CajaEntity.class, CajaEntity.getId()));
		return true;
	}

	@Override
	public CajaModel getCajaById(Integer id) {
		CajaEntity CajaEntity = this.em.find(CajaEntity.class, id);
		return CajaEntity != null ? new CajaAdapter(em, CajaEntity) : null;
	}

}
