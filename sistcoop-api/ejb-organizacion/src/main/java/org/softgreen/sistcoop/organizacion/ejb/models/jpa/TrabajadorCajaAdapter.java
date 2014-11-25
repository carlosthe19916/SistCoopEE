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

	public TrabajadorCajaAdapter(EntityManager em, TrabajadorCajaEntity TrabajadorCajaEntity) {
		this.em = em;
		this.trabajadorCajaEntity = TrabajadorCajaEntity;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrabajadorModel getTrabajador() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CajaModel getCaja() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCaja(CajaModel cajaModel) {
		// TODO Auto-generated method stub

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
