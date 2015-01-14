package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.BovedaCajaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialBovedaCajaEntity;

public class BovedaCajaAdapter implements BovedaCajaModel {

	private static final long serialVersionUID = 1L;

	protected BovedaCajaEntity bovedaCajaEntity;
	protected EntityManager em;

	public BovedaCajaAdapter(EntityManager em, BovedaCajaEntity bovedaCajaEntity) {
		this.em = em;
		this.bovedaCajaEntity = bovedaCajaEntity;
	}

	public BovedaCajaEntity getBovedaCajaEntity() {
		return bovedaCajaEntity;
	}

	@Override
	public void commit() {
		em.merge(bovedaCajaEntity);
	}

	@Override
	public Integer getId() {
		return bovedaCajaEntity.getId();
	}

	@Override
	public BigDecimal getSaldo() {
		return bovedaCajaEntity.getSaldo();
	}

	@Override
	public void setSaldo(BigDecimal saldo) {
		bovedaCajaEntity.setSaldo(saldo);
	}

	@Override
	public boolean getEstado() {
		return bovedaCajaEntity.isEstado();
	}

	@Override
	public void setEstado(boolean estado) {
		bovedaCajaEntity.setEstado(estado);
	}

	@Override
	public BovedaModel getBoveda() {
		return new BovedaAdapter(em, bovedaCajaEntity.getBoveda());
	}

	@Override
	public CajaModel getCaja() {
		return new CajaAdapter(em, bovedaCajaEntity.getCaja());
	}

	@Override
	public HistorialModel getHistorialActivo() {
		TypedQuery<HistorialBovedaCajaEntity> query = em.createNamedQuery(HistorialBovedaCajaEntity.findByEstado, HistorialBovedaCajaEntity.class);
		query.setParameter("idBovedaCaja", getId());
		query.setParameter("estado", true);
		List<HistorialBovedaCajaEntity> list = query.getResultList();
		if (list.size() > 0)
			return new HistorialBovedaCajaAdapter(em, list.get(0));
		else
			return null;
	}

	public static BovedaCajaEntity toBovedaCajaEntity(BovedaCajaModel model, EntityManager em) {
		if (model instanceof BovedaCajaAdapter) {
			return ((BovedaCajaAdapter) model).getBovedaCajaEntity();
		}
		return em.getReference(BovedaCajaEntity.class, model.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof BovedaCajaModel))
			return false;

		BovedaCajaModel that = (BovedaCajaModel) o;
		return that.getId().equals(getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

}
