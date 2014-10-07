package org.softgreen.sistcoop.persona.ejb.models.jpa;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.persona.clien.enums.TipoPersona;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.TipoDocumentoEntity;

public class TipoDocumentoAdapter implements TipoDocumentoModel {
	
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
		tipoDocumentoEntity.setAbreviatura(abreviatura);
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
		/*if (model instanceof TipoDocumentoAdapter) {
			return ((TipoDocumentoAdapter) model).getTipoDocumentEntity();
		}*/
		return em.getReference(TipoDocumentoEntity.class, model.getAbreviatura());
	}

	@Override
	public void commit() {
		em.merge(tipoDocumentoEntity);
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

}
