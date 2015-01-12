package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.util.Calendar;

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
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.BovedaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.CajaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialBovedaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialCajaEntity;
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
		Calendar calendar = Calendar.getInstance();
		
		CajaEntity cajaEntity = CajaAdapter.toCajaEntity(cajaModel, em);							
		HistorialCajaEntity historialEntity = new HistorialCajaEntity();	
		historialEntity.setCaja(cajaEntity);
		historialEntity.setFechaApertura(calendar.getTime());
		historialEntity.setHoraApertura(calendar.getTime());		
		historialEntity.setEstado(true);		
		em.persist(historialEntity);		
		return new HistorialCajaAdapter(em, historialEntity);
	}

	@Override
	public HistorialModel addHistorial(BovedaModel bovedaModel) {
		Calendar calendar = Calendar.getInstance();
		
		BovedaEntity bovedaEntity = BovedaAdapter.toBovedaEntity(bovedaModel, em);							
		HistorialBovedaEntity historialEntity = new HistorialBovedaEntity();	
		historialEntity.setBoveda(bovedaEntity);
		historialEntity.setFechaApertura(calendar.getTime());
		historialEntity.setHoraApertura(calendar.getTime());		
		historialEntity.setEstado(true);		
		em.persist(historialEntity);		
		return new HistorialBovedaAdapter(em, historialEntity);
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
