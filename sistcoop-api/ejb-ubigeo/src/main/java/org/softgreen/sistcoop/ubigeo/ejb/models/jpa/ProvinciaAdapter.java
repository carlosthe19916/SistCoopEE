package org.softgreen.sistcoop.ubigeo.ejb.models.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.ubigeo.client.models.DepartamentoModel;
import org.softgreen.sistcoop.ubigeo.client.models.DistritoModel;
import org.softgreen.sistcoop.ubigeo.client.models.ProvinciaModel;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.DepartamentoEntity;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.DistritoEntity;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.ProvinciaEntity;

public class ProvinciaAdapter implements ProvinciaModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ProvinciaEntity provinciaEntity;
	protected EntityManager em;

	public ProvinciaAdapter(EntityManager em, ProvinciaEntity provinciaEntity) {
		this.em = em;
		this.provinciaEntity = provinciaEntity;
	}

	public ProvinciaEntity getProvinciaEntity() {
		return provinciaEntity;
	}

	@Override
	public Integer getId() {
		return provinciaEntity.getId();
	}

	@Override
	public String getCodigo() {
		return provinciaEntity.getCodigo();
	}

	@Override
	public void setCodigo(String codigo) {
		provinciaEntity.setCodigo(codigo);
	}

	@Override
	public String getDenominacion() {
		return provinciaEntity.getDenominacion();
	}

	@Override
	public void setDenominacion(String denominacion) {
		provinciaEntity.setDenominacion(denominacion);
	}

	@Override
	public DepartamentoModel getDepartamento() {
		return new DepartamentoAdapter(em, provinciaEntity.getDepartamento());
	}

	@Override
	public void setDepartamento(DepartamentoModel departamentoModel) {
		DepartamentoEntity entity = DepartamentoAdapter.toDepartamentoEntity(departamentoModel, em);
		provinciaEntity.setDepartamento(entity);
	}

	@Override
	public Set<DistritoModel> getDistritos() {
		Set<DistritoEntity> distritos = provinciaEntity.getDistritos();
		Set<DistritoModel> result = new HashSet<DistritoModel>();
		for (DistritoEntity distritoEntity : distritos) {
			result.add(new DistritoAdapter(em, distritoEntity));
		}
		return result;
	}

	@Override
	public void setDistritos(Set<DistritoModel> distritos) {
		Set<DistritoEntity> result = new HashSet<DistritoEntity>();
		for (DistritoModel model : distritos) {
			result.add(DistritoAdapter.toDistritoEntity(model, em));
		}
		provinciaEntity.setDistritos(result);
	}

	public static ProvinciaEntity toProvinciaEntity(ProvinciaModel model, EntityManager em) {
		if (model instanceof ProvinciaAdapter) {
			return ((ProvinciaAdapter) model).getProvinciaEntity();
		}
		return em.getReference(ProvinciaEntity.class, model.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof ProvinciaModel))
			return false;

		ProvinciaModel that = (ProvinciaModel) o;
		return that.getId().equals(getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub

	}
}
