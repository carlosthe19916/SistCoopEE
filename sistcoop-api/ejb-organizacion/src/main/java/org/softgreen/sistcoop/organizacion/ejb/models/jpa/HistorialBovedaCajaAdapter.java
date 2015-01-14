package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialBovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleHistorialEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialBovedaCajaEntity;

public class HistorialBovedaCajaAdapter implements HistorialBovedaCajaModel {

	private static final long serialVersionUID = 1L;

	protected HistorialBovedaCajaEntity historialCajaEntity;
	protected EntityManager em;

	public HistorialBovedaCajaAdapter(EntityManager em, HistorialBovedaCajaEntity HistorialCajaEntity) {
		this.em = em;
		this.historialCajaEntity = HistorialCajaEntity;
	}

	public HistorialBovedaCajaEntity getHistorialCajaEntity() {
		return historialCajaEntity;
	}

	@Override
	public void commit() {
		em.merge(historialCajaEntity);
	}

	@Override
	public Long getId() {
		return historialCajaEntity.getId();
	}

	@Override
	public boolean getEstado() {
		return historialCajaEntity.isEstado();
	}

	@Override
	public void setEstado(boolean estado) {
		historialCajaEntity.setEstado(estado);
	}

	@Override
	public Date getFechaApertura() {
		return historialCajaEntity.getFechaApertura();
	}

	@Override
	public Date getFechaCierre() {
		return historialCajaEntity.getFechaCierre();
	}

	@Override
	public void setFechaCierre(Date fechaCierre) {
		historialCajaEntity.setFechaCierre(fechaCierre);
	}

	@Override
	public Date getHoraApertura() {
		return historialCajaEntity.getHoraApertura();
	}

	@Override
	public Date getHoraCierre() {
		return historialCajaEntity.getHoraCierre();
	}

	@Override
	public void setHoraCierre(Date horaCierre) {
		historialCajaEntity.setHoraCierre(horaCierre);
	}

	@Override
	public BigDecimal getSaldo() {
		Set<DetalleHistorialEntity> detalleHistorialEntities = historialCajaEntity.getDetalle();
		BigDecimal saldo = BigDecimal.ZERO;
		for (DetalleHistorialEntity detalleHistorialEntity : detalleHistorialEntities) {
			saldo = saldo.add(detalleHistorialEntity.getSubtotal());
		}
		return saldo;
	}

	@Override
	public List<DetalleHistorialModel> getDetalle() {
		Set<DetalleHistorialEntity> detalleHistorialEntities = historialCajaEntity.getDetalle();
		List<DetalleHistorialModel> result = new ArrayList<DetalleHistorialModel>();
		for (DetalleHistorialEntity detalleHistorialEntity : detalleHistorialEntities) {
			result.add(new DetalleHistorialAdapter(em, detalleHistorialEntity));
		}
		return result;
	}

	@Override
	public BovedaCajaModel getBovedaCaja() {
		return new BovedaCajaAdapter(em, historialCajaEntity.getBovedaCaja());
	}

	public static HistorialBovedaCajaEntity toSucursalEntity(HistorialModel model, EntityManager em) {
		if (model instanceof HistorialBovedaCajaAdapter) {
			return ((HistorialBovedaCajaAdapter) model).getHistorialCajaEntity();
		}
		return em.getReference(HistorialBovedaCajaEntity.class, model.getId());
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
