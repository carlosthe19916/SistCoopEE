package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.BovedaCajaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.BovedaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialBovedaEntity;

public class BovedaAdapter implements BovedaModel {

	private static final long serialVersionUID = 1L;

	protected BovedaEntity bovedaEntity;
	protected EntityManager em;

	public BovedaAdapter(EntityManager em, BovedaEntity bovedaEntity) {
		this.em = em;
		this.bovedaEntity = bovedaEntity;
	}

	public BovedaEntity getBovedaEntity() {
		return bovedaEntity;
	}

	@Override
	public void commit() {
		em.merge(bovedaEntity);
	}

	@Override
	public Integer getId() {
		return bovedaEntity.getId();
	}

	@Override
	public String getMoneda() {
		return bovedaEntity.getMoneda();
	}

	@Override
	public String getDenominacion() {
		return bovedaEntity.getDenominacion();
	}

	@Override
	public void setDenominacion(String denominacion) {
		bovedaEntity.setDenominacion(denominacion);
	}

	@Override
	public boolean isAbierto() {
		return bovedaEntity.isAbierto();
	}

	@Override
	public void setAbierto(boolean abierto) {
		bovedaEntity.setAbierto(abierto);
	}

	@Override
	public boolean getEstadoMovimiento() {
		return bovedaEntity.isEstadoMovimiento();
	}

	@Override
	public void setEstadoMovimiento(boolean estadoMovimiento) {
		bovedaEntity.setEstadoMovimiento(estadoMovimiento);
	}

	@Override
	public boolean getEstado() {
		return bovedaEntity.isEstado();
	}

	@Override
	public void setEstado(boolean estado) {
		bovedaEntity.setEstado(estado);
	}

	@Override
	public AgenciaModel getAgencia() {
		return new AgenciaAdapter(em, bovedaEntity.getAgencia());
	}	

	@Override
	public HistorialModel getHistorialActivo() {
		TypedQuery<HistorialBovedaEntity> query = em.createNamedQuery(HistorialBovedaEntity.findByEstado, HistorialBovedaEntity.class);
		query.setParameter("idBoveda", getId());
		query.setParameter("estado", true);
		List<HistorialBovedaEntity> list = query.getResultList();
		if (list.size() > 0)
			return new HistorialBovedaAdapter(em, list.get(0));
		else
			return null;
	}

	@Override
	public List<BovedaCajaModel> getBovedaCajas() {
		Set<BovedaCajaEntity> bovedaCajaEntities = bovedaEntity.getBovedaCajas();
		List<BovedaCajaModel> result = new ArrayList<BovedaCajaModel>();
		for (BovedaCajaEntity bovedaCajaEntity : bovedaCajaEntities) {
			if (bovedaCajaEntity.isEstado())
				result.add(new BovedaCajaAdapter(em, bovedaCajaEntity));
		}
		return result;
	}

	public static BovedaEntity toBovedaEntity(BovedaModel model, EntityManager em) {
		if (model instanceof BovedaAdapter) {
			return ((BovedaAdapter) model).getBovedaEntity();
		}
		return em.getReference(BovedaEntity.class, model.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof BovedaModel))
			return false;

		BovedaModel that = (BovedaModel) o;
		return that.getId().equals(getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

}
