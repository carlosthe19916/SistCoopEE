package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TrabajadorEntity;

public class TrabajadorAdapter implements TrabajadorModel {

	private static final long serialVersionUID = 1L;

	protected TrabajadorEntity bovedaEntity;
	protected EntityManager em;

	public TrabajadorAdapter(EntityManager em, TrabajadorEntity bovedaEntity) {
		this.em = em;
		this.bovedaEntity = bovedaEntity;
	}

	public TrabajadorEntity getTrabajadorEntity() {
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
	public String getTipoDocumento() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTipoDocumento(String tipoDocumento) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getNumeroDocumento() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNumeroDocumento(String numeroDocumento) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getUsuario() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUsuario(String usuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getEstado() {
		// TODO Auto-generated method stub
		return null;
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
	public void setAgencia(AgenciaModel agenciaModel) {
		// TODO Auto-generated method stub

	}

	public static TrabajadorEntity toTrabajadorEntity(TrabajadorModel model, EntityManager em) {
		if (model instanceof TrabajadorAdapter) {
			return ((TrabajadorAdapter) model).getTrabajadorEntity();
		}
		return em.getReference(TrabajadorEntity.class, model.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof TrabajadorModel))
			return false;

		TrabajadorModel that = (TrabajadorModel) o;
		return that.getId().equals(getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

}
