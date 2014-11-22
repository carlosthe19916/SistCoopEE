package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.CajaEntity;

public class CajaAdapter implements CajaModel {

	private static final long serialVersionUID = 1L;

	protected CajaEntity bovedaEntity;
	protected EntityManager em;

	public CajaAdapter(EntityManager em, CajaEntity bovedaEntity) {
		this.em = em;
		this.bovedaEntity = bovedaEntity;
	}

	public CajaEntity getCajaEntity() {
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

	@Override
	public HistorialModel getHistorialActivo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HistorialModel> getHistoriales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BovedaCajaModel getBovedaCaja(BovedaModel bovedaModel) {
		// TODO Auto-generated method stub
		return null;
	}

	public static CajaEntity toCajaEntity(CajaModel model, EntityManager em) {
		if (model instanceof CajaAdapter) {
			return ((CajaAdapter) model).getCajaEntity();
		}
		return em.getReference(CajaEntity.class, model.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof CajaModel))
			return false;

		CajaModel that = (CajaModel) o;
		return that.getId().equals(getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

}
