package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.BovedaCajaEntity;

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
	public BovedaModel getBoveda() {
		return new BovedaAdapter(em, bovedaCajaEntity.getBoveda());
	}

	@Override
	public CajaModel getCaja() {
		return new CajaAdapter(em, bovedaCajaEntity.getCaja());
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
