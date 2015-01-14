package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleHistorialEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialEntity;

public class HistorialAdapter implements HistorialModel {

	private static final long serialVersionUID = 1L;

	protected HistorialEntity historialEntity;
	protected EntityManager em;

	public HistorialAdapter(EntityManager em, HistorialEntity historialEntity) {
		this.em = em;
		this.historialEntity = historialEntity;
	}

	public HistorialEntity getHistorialEntity() {
		return historialEntity;
	}

	@Override
	public void commit() {
		em.merge(historialEntity);
	}

	@Override
	public Long getId() {
		return historialEntity.getId();
	}

	@Override
	public boolean getEstado() {
		return historialEntity.isEstado();
	}

	@Override
	public void setEstado(boolean estado) {
		historialEntity.setEstado(estado);
	}

	@Override
	public Date getFechaApertura() {
		return historialEntity.getFechaApertura();
	}

	@Override
	public Date getFechaCierre() {
		return historialEntity.getFechaCierre();
	}

	@Override
	public void setFechaCierre(Date fechaCierre) {
		historialEntity.setFechaCierre(fechaCierre);
	}

	@Override
	public Date getHoraApertura() {
		return historialEntity.getHoraApertura();
	}

	@Override
	public Date getHoraCierre() {
		return historialEntity.getHoraCierre();
	}

	@Override
	public void setHoraCierre(Date horaCierre) {
		historialEntity.setHoraCierre(horaCierre);
	}

	@Override
	public BigDecimal getSaldo() {
		Set<DetalleHistorialEntity> detalleHistorialEntities = historialEntity.getDetalle();
		BigDecimal saldo = BigDecimal.ZERO;
		for (DetalleHistorialEntity detalleHistorialEntity : detalleHistorialEntities) {
			saldo = saldo.add(detalleHistorialEntity.getSubtotal());
		}
		return saldo;
	}

	@Override
	public List<DetalleHistorialModel> getDetalle() {
		Set<DetalleHistorialEntity> detalleHistorialEntities = historialEntity.getDetalle();
		List<DetalleHistorialModel> result = new ArrayList<DetalleHistorialModel>();
		for (DetalleHistorialEntity detalleHistorialEntity : detalleHistorialEntities) {
			result.add(new DetalleHistorialAdapter(em, detalleHistorialEntity));
		}
		return result;
	}

	public static HistorialEntity toHistorialEntity(HistorialModel model, EntityManager em) {
		if (model instanceof HistorialAdapter) {
			return ((HistorialAdapter) model).getHistorialEntity();
		}
		return em.getReference(HistorialEntity.class, model.getId());
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
