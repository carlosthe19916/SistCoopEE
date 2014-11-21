package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.BovedaEntity;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMoneda() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDenominacion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDenominacion(String denominacion) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAbierto() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAbierto(boolean abierto) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getEstadoMovimiento() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setEstadoMovimiento(boolean estadoMovimiento) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getEstado() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setEstado(boolean estado) {
		// TODO Auto-generated method stub

	}

	@Override
	public AgenciaModel getAgencia() {
		// TODO Auto-generated method stub
		return null;
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
