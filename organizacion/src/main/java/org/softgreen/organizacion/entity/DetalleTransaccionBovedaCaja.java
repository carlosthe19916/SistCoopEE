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
@DiscriminatorValue(value = "BovedaCaja")
public class DetalleTransaccionBovedaCaja extends DetalleTransaccionInterna implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TransaccionBovedaCaja transaccionBovedaCaja;

	public DetalleTransaccionBovedaCaja() {
		// TODO Auto-generated constructor stub
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public TransaccionBovedaCaja getTransaccionBovedaCaja() {
		return transaccionBovedaCaja;
	}

	public void setTransaccionBovedaCaja(TransaccionBovedaCaja transaccionBovedaCaja) {
		this.transaccionBovedaCaja = transaccionBovedaCaja;
	}
}
