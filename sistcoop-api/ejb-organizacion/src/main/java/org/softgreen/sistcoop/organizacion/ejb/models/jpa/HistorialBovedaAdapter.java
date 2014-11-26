package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialBovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleHistorialEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialBovedaEntity;

public class HistorialBovedaAdapter implements HistorialBovedaModel {

	private static final long serialVersionUID = 1L;

	protected HistorialBovedaEntity historialBovedaEntity;
	protected EntityManager em;

	public HistorialBovedaAdapter(EntityManager em, HistorialBovedaEntity HistorialBovedaEntity) {
		this.em = em;
		this.historialBovedaEntity = HistorialBovedaEntity;
	}

	public HistorialBovedaEntity getHistorialBovedaEntity() {
		return historialBovedaEntity;
	}

	@Override
	public void commit() {
		em.merge(historialBovedaEntity);
	}

	@Override
	public Long getId() {
		return historialBovedaEntity.getId();
	}

	@Override
	public boolean getEstado() {
		return historialBovedaEntity.isEstado();
	}

	@Override
	public void setEstado(boolean estado) {
		historialBovedaEntity.setEstado(estado);
	}

	@Override
	public Date getFechaApertura() {
		return historialBovedaEntity.getFechaApertura();
	}

	@Override
	public Date getFechaCierre() {
		return historialBovedaEntity.getFechaCierre();
	}

	@Override
	public void setFechaCierre(Date fechaCierre) {
		historialBovedaEntity.setFechaCierre(fechaCierre);
	}

	@Override
	public Date getHoraApertura() {
		return historialBovedaEntity.getHoraApertura();
	}

	@Override
	public Date getHoraCierre() {
		return historialBovedaEntity.getHoraCierre();
	}

	@Override
	public void setHoraCierre(Date horaCierre) {
		historialBovedaEntity.setHoraCierre(horaCierre);
	}

	@Override
	public BigDecimal getSaldo() {
		Set<DetalleHistorialEntity> detalleHistorialEntities = historialBovedaEntity.getDetalle();
		BigDecimal saldo = BigDecimal.ZERO;
		for (DetalleHistorialEntity detalleHistorialEntity : detalleHistorialEntities) {
			BigDecimal subtotal = detalleHistorialEntity.getValor().multiply(new BigDecimal(detalleHistorialEntity.getCantidad()));
			saldo = saldo.add(subtotal);
		}
		return saldo;
	}

	@Override
	public List<DetalleHistorialModel> getDetalle() {
		Set<DetalleHistorialEntity> detalleHistorialEntities = historialBovedaEntity.getDetalle();
		List<DetalleHistorialModel> result = new ArrayList<DetalleHistorialModel>();
		for (DetalleHistorialEntity detalleHistorialEntity : detalleHistorialEntities) {
			result.add(new DetalleHistorialAdapter(em, detalleHistorialEntity));
		}
		return result;
	}

	@Override
	public BovedaModel getBoveda() {
		return new BovedaAdapter(em, historialBovedaEntity.getBoveda());
	}

	public static HistorialBovedaEntity toSucursalEntity(HistorialModel model, EntityManager em) {
		if (model instanceof HistorialBovedaAdapter) {
			return ((HistorialBovedaAdapter) model).getHistorialBovedaEntity();
		}
		return em.getReference(HistorialBovedaEntity.class, model.getId());
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
