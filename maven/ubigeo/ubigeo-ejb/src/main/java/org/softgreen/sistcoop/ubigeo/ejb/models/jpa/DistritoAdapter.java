package org.softgreen.sistcoop.ubigeo.ejb.models.jpa;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.ubigeo.client.models.DepartamentoModel;
import org.softgreen.sistcoop.ubigeo.client.models.DistritoModel;
import org.softgreen.sistcoop.ubigeo.client.models.ProvinciaModel;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.DistritoEntity;

public class DistritoAdapter implements DistritoModel {

	protected DistritoEntity distritoEntity;
	protected EntityManager em;

	public DistritoAdapter(EntityManager em, DistritoEntity distritoEntity) {
		this.em = em;
		this.distritoEntity = distritoEntity;
	}

	public DistritoEntity getDepartamentoEntity() {
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

	@Override
	public ProvinciaModel getProvincia() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProvincia(ProvinciaModel provinciaModel) {
		// TODO Auto-generated method stub

	}

}
