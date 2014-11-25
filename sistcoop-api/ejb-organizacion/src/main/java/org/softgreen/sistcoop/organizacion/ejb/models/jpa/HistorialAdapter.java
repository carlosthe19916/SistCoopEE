package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialEntity;

public class HistorialAdapter implements HistorialModel {

	private static final long serialVersionUID = 1L;

	protected HistorialEntity HistorialEntity;
	protected EntityManager em;

	public HistorialAdapter(EntityManager em, HistorialEntity HistorialEntity) {
		this.em = em;
		this.HistorialEntity = HistorialEntity;
	}

	public HistorialEntity getHistorialEntity() {
		return HistorialEntity;
	}

	@Override
	public void commit() {
		em.merge(HistorialEntity);
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
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
	public Date getFechaApertura() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getFechaCierre() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFechaCierre(Date fechaCierre) {
		// TODO Auto-generated method stub

	}

	@Override
	public Date getHoraApertura() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getHoraCierre() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHoraCierre(Date horaCierre) {
		// TODO Auto-generated method stub

	}

	@Override
	public BigDecimal getSaldo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DetalleHistorialModel> getDetalle() {
		// TODO Auto-generated method stub
		return null;
	}

	public static HistorialEntity toSucursalEntity(HistorialModel model, EntityManager em) {
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
