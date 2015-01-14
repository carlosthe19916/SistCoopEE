package org.softgreen.sistcoop.ubigeo.ejb.models.jpa;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.ubigeo.client.models.DepartamentoModel;
import org.softgreen.sistcoop.ubigeo.client.models.DistritoModel;
import org.softgreen.sistcoop.ubigeo.client.models.ProvinciaModel;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.DistritoEntity;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.ProvinciaEntity;

public class DistritoAdapter implements DistritoModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected DistritoEntity distritoEntity;
	protected EntityManager em;

	public DistritoAdapter(EntityManager em, DistritoEntity distritoEntity) {
		this.em = em;
		this.distritoEntity = distritoEntity;
	}

	public DistritoEntity getDistritoEntity() {
		return distritoEntity;
	}

	@Override
	public Integer getId() {
		return distritoEntity.getId();
	}

	@Override
	public String getCodigo() {
		return distritoEntity.getCodigo();
	}

	@Override
	public void setCodigo(String codigo) {
		distritoEntity.setCodigo(codigo);
	}

	@Override
	public String getDenominacion() {
		return distritoEntity.getDenominacion();
	}

	@Override
	public void setDenominacion(String denominacion) {
		distritoEntity.setDenominacion(denominacion);
	}

	@Override
	public ProvinciaModel getProvincia() {
		return new ProvinciaAdapter(em, distritoEntity.getProvincia());
	}

	@Override
	public void setProvincia(ProvinciaModel provinciaModel) {
		ProvinciaEntity provinciaEntity = ProvinciaAdapter.toProvinciaEntity(provinciaModel, em);
		distritoEntity.setProvincia(provinciaEntity);
	}

	@Override
	public void commit() {
		em.merge(distritoEntity);
	}

	public static DistritoEntity toDistritoEntity(DistritoModel model, EntityManager em) {
		if (model instanceof DistritoAdapter) {
			return ((DistritoAdapter) model).getDistritoEntity();
		}
		return em.getReference(DistritoEntity.class, model.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof DepartamentoModel))
			return false;

		DepartamentoModel that = (DepartamentoModel) o;
		return that.getId().equals(getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

}
