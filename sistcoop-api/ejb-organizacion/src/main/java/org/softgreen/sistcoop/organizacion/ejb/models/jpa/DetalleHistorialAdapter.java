package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleHistorialEntity;

public class DetalleHistorialAdapter implements DetalleHistorialModel {

	private static final long serialVersionUID = 1L;

	protected DetalleHistorialEntity detalleHistorialEntity;
	protected EntityManager em;

	public DetalleHistorialAdapter(EntityManager em, DetalleHistorialEntity detalleHistorialEntity) {
		this.em = em;
		this.detalleHistorialEntity = detalleHistorialEntity;
	}

	public DetalleHistorialEntity getDetalleHistorialEntity() {
		return detalleHistorialEntity;
	}

	@Override
	public void commit() {
		em.merge(detalleHistorialEntity);
	}

	@Override
	public Long getId() {
		return detalleHistorialEntity.getId();
	}

	@Override
	public BigDecimal getValor() {
		return detalleHistorialEntity.getValor();
	}

	@Override
	public int getCantidad() {
		return detalleHistorialEntity.getCantidad();
	}

	@Override
	public void setCantidad(int cantidad) {
		detalleHistorialEntity.setCantidad(cantidad);
	}

	public static DetalleHistorialEntity toSucursalEntity(HistorialModel model, EntityManager em) {
		if (model instanceof DetalleHistorialAdapter) {
			return ((DetalleHistorialAdapter) model).getDetalleHistorialEntity();
		}
		return em.getReference(DetalleHistorialEntity.class, model.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof HistorialModel))
			return false;

		HistorialModel that = (HistorialModel) o;
		return that.getId().equals(getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

}
