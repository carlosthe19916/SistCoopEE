package org.softgreen.sistcoop.ubigeo.ejb.models.jpa;

import java.util.Set;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.ubigeo.client.models.DepartamentoModel;
import org.softgreen.sistcoop.ubigeo.client.models.DistritoModel;
import org.softgreen.sistcoop.ubigeo.client.models.ProvinciaModel;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.ProvinciaEntity;

public class ProvinciaAdapter implements ProvinciaModel {

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

	@Override
	public DepartamentoModel getDepartamento() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDepartamento(DepartamentoModel departamentoModel) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<DistritoModel> getDistritos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDistritos(Set<DistritoModel> provincias) {
		// TODO Auto-generated method stub

	}

}
