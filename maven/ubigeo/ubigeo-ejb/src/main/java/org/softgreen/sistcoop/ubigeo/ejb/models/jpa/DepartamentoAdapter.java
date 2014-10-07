package org.softgreen.sistcoop.ubigeo.ejb.models.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.ubigeo.client.models.DepartamentoModel;
import org.softgreen.sistcoop.ubigeo.client.models.ProvinciaModel;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.DepartamentoEntity;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.ProvinciaEntity;

public class DepartamentoAdapter implements DepartamentoModel {

	protected DepartamentoEntity departamentoEntity;
	protected EntityManager em;

	public DepartamentoAdapter(EntityManager em, DepartamentoEntity departamentoEntity) {
		this.em = em;
		this.departamentoEntity = departamentoEntity;
	}

	public DepartamentoEntity getDepartamentoEntity() {
		return departamentoEntity;
	}

	@Override
	public Integer getId() {
		return departamentoEntity.getId();
	}

	@Override
	public String getCodigo() {
		return departamentoEntity.getCodigo();
	}

	@Override
	public void setCodigo(String codigo) {
		departamentoEntity.setCodigo(codigo);
	}

	@Override
	public String getDenominacion() {
		return departamentoEntity.getDenominacion();
	}

	@Override
	public void setDenominacion(String denominacion) {
		departamentoEntity.setDenominacion(denominacion);
	}

	@Override
	public Set<ProvinciaModel> getProvincias() {
		Set<ProvinciaEntity> provincias = departamentoEntity.getProvincias();
		Set<ProvinciaModel> result = new HashSet<ProvinciaModel>();
		for (ProvinciaEntity provinciaEntity : provincias) {
			result.add(new ProvinciaAdapter(em, provinciaEntity));
		}
		return result;
	}

	@Override
	public void setProvincias(Set<ProvinciaModel> provincias) {
		Set<ProvinciaEntity> result = new HashSet<ProvinciaEntity>();
		for (ProvinciaModel model : provincias) {
			result.add(ProvinciaAdapter.toProvinciaEntity(model, em));
		}
		departamentoEntity.setProvincias(result);
	}

	public static DepartamentoEntity toDepartamentoEntity(DepartamentoModel model, EntityManager em) {
		if (model instanceof DepartamentoAdapter) {
			return ((DepartamentoAdapter) model).getDepartamentoEntity();
		}
		return em.getReference(DepartamentoEntity.class, model.getId());
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

	@Override
	public void commit() {
		// TODO Auto-generated method stub

	}

}
