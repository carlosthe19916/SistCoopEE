package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialBovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialBovedaEntity;

public class HistorialCajaAdapter implements HistorialBovedaModel {

	private static final long serialVersionUID = 1L;

	protected HistorialBovedaEntity historialBovedaEntity;
	protected EntityManager em;

	public HistorialCajaAdapter(EntityManager em, HistorialBovedaEntity HistorialBovedaEntity) {
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

	@Override
	public BovedaModel getBoveda() {
		// TODO Auto-generated method stub
		return null;
	}

	public static HistorialBovedaEntity toSucursalEntity(HistorialModel model, EntityManager em) {
		if (model instanceof HistorialCajaAdapter) {
			return ((HistorialCajaAdapter) model).getHistorialBovedaEntity();
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
