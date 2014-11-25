package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.CajaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialBovedaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialCajaEntity;

public class CajaAdapter implements CajaModel {

	private static final long serialVersionUID = 1L;

	protected CajaEntity cajaEntity;
	protected EntityManager em;

	public CajaAdapter(EntityManager em, CajaEntity cajaEntity) {
		this.em = em;
		this.cajaEntity = cajaEntity;
	}

	public CajaEntity getCajaEntity() {
		return cajaEntity;
	}

	@Override
	public void commit() {
		em.merge(cajaEntity);
	}

	@Override
	public Integer getId() {
		return cajaEntity.getId();
	}

	@Override
	public String getDenominacion() {
		return cajaEntity.getDenominacion();
	}

	@Override
	public void setDenominacion(String denominacion) {
		cajaEntity.setDenominacion(denominacion);
	}

	@Override
	public boolean isAbierto() {
		return cajaEntity.isAbierto();
	}

	@Override
	public void setAbierto(boolean abierto) {
		cajaEntity.setAbierto(abierto);
	}

	@Override
	public boolean getEstadoMovimiento() {
		return cajaEntity.isEstadoMovimiento();
	}

	@Override
	public void setEstadoMovimiento(boolean estadoMovimiento) {
		cajaEntity.setEstadoMovimiento(estadoMovimiento);
	}

	@Override
	public boolean getEstado() {
		cajaEntity.isEstado();
	}

	@Override
	public void setEstado(boolean estado) {
		cajaEntity.setEstado(estado);
	}

	@Override
	public AgenciaModel getAgencia() {
		return new AgenciaAdapter(em, cajaEntity.getAgencia());
	}

	@Override
	public HistorialModel getHistorialActivo() {
		TypedQuery<HistorialCajaEntity> query = em.createNamedQuery(HistorialCajaEntity.findByEstado, HistorialCajaEntity.class);
		query.setParameter("estado", true);
		List<HistorialCajaEntity> list = query.getResultList();
		if (list.size() > 0)
			return new HistorialCajaAdapter(em, list.get(0));
		else
			return null;
	}

	@Override
	public List<BovedaCajaModel> getBovedaCajas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TrabajadorCajaModel> getTrabajadorCajas() {
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
