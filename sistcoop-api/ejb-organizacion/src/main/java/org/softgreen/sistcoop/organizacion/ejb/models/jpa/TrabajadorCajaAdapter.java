package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TrabajadorCajaEntity;

public class TrabajadorCajaAdapter implements TrabajadorCajaModel {

	private static final long serialVersionUID = 1L;

	protected TrabajadorCajaEntity trabajadorCajaEntity;
	protected EntityManager em;

	public TrabajadorCajaAdapter(EntityManager em, TrabajadorCajaEntity trabajadorCajaEntity) {
		this.em = em;
		this.trabajadorCajaEntity = trabajadorCajaEntity;
	}

	public TrabajadorCajaEntity getTrabajadorCajaEntity() {
		return trabajadorCajaEntity;
	}

	@Override
	public void commit() {
		em.merge(trabajadorCajaEntity);
	}

	@Override
	public Integer getId() {
		return trabajadorCajaEntity.getId();
	}

	@Override
	public TrabajadorModel getTrabajador() {
		return new TrabajadorAdapter(em, trabajadorCajaEntity.getTrabajador());
	}

	@Override
	public CajaModel getCaja() {
		return new CajaAdapter(em, trabajadorCajaEntity.getCaja());
	}

	public static TrabajadorCajaEntity toTrabajadorCajaEntity(TrabajadorCajaModel model, EntityManager em) {
		if (model instanceof TrabajadorCajaAdapter) {
			return ((TrabajadorCajaAdapter) model).getTrabajadorCajaEntity();
		}
		return em.getReference(TrabajadorCajaEntity.class, model.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof TrabajadorCajaModel))
			return false;

		TrabajadorCajaModel that = (TrabajadorCajaModel) o;
		return that.getId().equals(getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

}
