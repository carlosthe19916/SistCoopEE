package org.softgreen.sistcoop.ubigeo.ejb.models.jpa;

import java.util.Set;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.ubigeo.client.models.DepartamentoModel;
import org.softgreen.sistcoop.ubigeo.client.models.ProvinciaModel;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.DepartamentoEntity;

public class DepartamentoAdapter implements DepartamentoModel {

	protected DepartamentoEntity departamentoEntity;
	protected EntityManager em;

	public DepartamentoAdapter(EntityManager em, DepartamentoEntity departamentoEntity) {
		this.em = em;
		this.departamentoEntity = departamentoEntity;
	}

	public DepartamentoEntity getCountryEntity() {
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
		return null;
	}

	@Override
	public void setProvincias(Set<ProvinciaModel> provincias) {
		// TODO Auto-generated method stub

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
