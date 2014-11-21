package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.organizacion.client.models.SucursalModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.SucursalEntity;

public class SucursalAdapter implements SucursalModel {

	private static final long serialVersionUID = 1L;

	protected SucursalEntity sucursalEntity;
	protected EntityManager em;

	public SucursalAdapter(EntityManager em, SucursalEntity sucursalEntity) {
		this.em = em;
		this.sucursalEntity = sucursalEntity;
	}

	public SucursalEntity getSucursalEntity() {
		return sucursalEntity;
	}

	@Override
	public void commit() {
		em.merge(sucursalEntity);
	}

	@Override
	public Integer getId() {
		return sucursalEntity.getId();
	}

	@Override
	public String getDenominacion() {
		return sucursalEntity.getDenominacion();
	}

	@Override
	public void setDenominacion(String denominacion) {
		sucursalEntity.setDenominacion(denominacion);
	}

	@Override
	public String getAbreviatura() {
		return sucursalEntity.getAbreviatura();
	}

	@Override
	public void setAbreviatura(String abreviatura) {
		sucursalEntity.setAbreviatura(abreviatura);
	}

	@Override
	public boolean getEstado() {
		return sucursalEntity.isEstado();
	}

	@Override
	public void setEstado(boolean estado) {
		sucursalEntity.setEstado(estado);
	}

	public static SucursalEntity toSucursalEntity(SucursalModel model, EntityManager em) {
		if (model instanceof SucursalAdapter) {
			return ((SucursalAdapter) model).getSucursalEntity();
		}
		return em.getReference(SucursalEntity.class, model.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof SucursalModel))
			return false;

		SucursalModel that = (SucursalModel) o;
		return that.getId().equals(getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

}
