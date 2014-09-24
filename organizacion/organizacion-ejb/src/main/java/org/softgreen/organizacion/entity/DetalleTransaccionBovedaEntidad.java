package org.softgreen.organizacion.entity;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue(value = "BovedaEntidad")
public class DetalleTransaccionBovedaEntidad extends DetalleTransaccionInterna implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TransaccionBovedaEntidad transaccionBovedaEntidad;

	public DetalleTransaccionBovedaEntidad() {
		// TODO Auto-generated constructor stub
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public TransaccionBovedaEntidad getTransaccionBovedaEntidad() {
		return transaccionBovedaEntidad;
	}

	public void setTransaccionBovedaEntidad(TransaccionBovedaEntidad transaccionBovedaEntidad) {
		this.transaccionBovedaEntidad = transaccionBovedaEntidad;
	}
}
