package org.softgreen.organizacion.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@DiscriminatorValue("boveda")
public class HistorialBoveda extends Historial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Boveda boveda;
	private Set<TransaccionBovedaCaja> transaccionesBovedaCaja = new HashSet<TransaccionBovedaCaja>();
	private Set<TransaccionBovedaEntidad> transaccionesBovedaEntidad = new HashSet<TransaccionBovedaEntidad>();

	public HistorialBoveda() {
		// TODO Auto-generated constructor stub
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public Boveda getBoveda() {
		return boveda;
	}

	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialBoveda")
	public Set<TransaccionBovedaCaja> getTransaccionesBovedaCaja() {
		return transaccionesBovedaCaja;
	}

	public void setTransaccionesBovedaCaja(Set<TransaccionBovedaCaja> transaccionesBovedaCaja) {
		this.transaccionesBovedaCaja = transaccionesBovedaCaja;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialBoveda")
	public Set<TransaccionBovedaEntidad> getTransaccionesBovedaEntidad() {
		return transaccionesBovedaEntidad;
	}

	public void setTransaccionesBovedaEntidad(Set<TransaccionBovedaEntidad> transaccionesBovedaEntidad) {
		this.transaccionesBovedaEntidad = transaccionesBovedaEntidad;
	}
}
