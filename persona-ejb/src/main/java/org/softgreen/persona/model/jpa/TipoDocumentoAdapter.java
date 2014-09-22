package org.softgreen.persona.model.jpa;

import javax.persistence.EntityManager;

import org.softgreen.persona.model.TipoDocumentoModel;
import org.softgreen.persona.model.type.TipoPersona;

public class TipoDocumentoAdapter implements TipoDocumentoModel {

	protected TipoDocumentoModel tipoDocumentoModel;
	protected EntityManager em;

	public TipoDocumentoAdapter(EntityManager em,
			TipoDocumentoModel tipoDocumentoModel) {
		this.em = em;
		this.tipoDocumentoModel = tipoDocumentoModel;
	}

	@Override
	public String getAbreviatura() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDenominacion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDenominacion(String denominacion) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCantidadCaracteres() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCantidadCaracteres(int cantidadCaracteres) {
		// TODO Auto-generated method stub

	}

	@Override
	public TipoPersona getTipoPersona() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTipoPersona(TipoPersona tipoPersona) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof TipoDocumentoModel))
			return false;

		TipoDocumentoModel that = (TipoDocumentoModel) o;
		return that.getAbreviatura().equals(getAbreviatura());
	}

	@Override
	public int hashCode() {
		return getAbreviatura().hashCode();
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub
		
	}
}
