package org.softgreen.persona.model.jpa;

import javax.persistence.EntityManager;

import org.softgreen.persona.model.TipoDocumentoModel;
import org.softgreen.persona.model.jpa.entity.TipoDocumentoEntity;
import org.softgreen.persona.model.type.TipoPersona;

public class TipoDocumentoAdapter implements TipoDocumentoModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected TipoDocumentoEntity tipoDocumentoEntity;
	protected EntityManager em;

	public TipoDocumentoAdapter(EntityManager em, TipoDocumentoEntity tipoDocumentoEntity) {
		this.em = em;
		this.tipoDocumentoEntity = tipoDocumentoEntity;
	}

	public TipoDocumentoEntity getTipoDocumentEntity() {
		return tipoDocumentoEntity;
	}

	@Override
	public String getAbreviatura() {
		return tipoDocumentoEntity.getAbreviatura();
	}

	@Override
	public void setAbreviatura(String abreviatura) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDenominacion() {
		return tipoDocumentoEntity.getDenominacion();
	}

	@Override
	public void setDenominacion(String denominacion) {
		tipoDocumentoEntity.setDenominacion(denominacion);
	}

	@Override
	public int getCantidadCaracteres() {
		return tipoDocumentoEntity.getCantidadCaracteres();
	}

	@Override
	public void setCantidadCaracteres(int cantidadCaracteres) {
		tipoDocumentoEntity.setCantidadCaracteres(cantidadCaracteres);
	}

	@Override
	public TipoPersona getTipoPersona() {
		return tipoDocumentoEntity.getTipoPersona();
	}

	@Override
	public void setTipoPersona(TipoPersona tipoPersona) {
		tipoDocumentoEntity.setTipoPersona(tipoPersona);
	}

	public static TipoDocumentoEntity toTipoDocumentoEntity(TipoDocumentoModel model, EntityManager em) {
		if (model instanceof TipoDocumentoAdapter) {
			return ((TipoDocumentoAdapter) model).getTipoDocumentEntity();
		}
		return em.getReference(TipoDocumentoEntity.class, model.getAbreviatura());
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
		em.merge(tipoDocumentoEntity);
	}

}
