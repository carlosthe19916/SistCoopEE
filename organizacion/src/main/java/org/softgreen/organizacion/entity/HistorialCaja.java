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
@DiscriminatorValue("caja")
public class HistorialCaja extends Historial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Caja caja;
	private Set<TransaccionBovedaCaja> transaccionesBovedaCaja = new HashSet<TransaccionBovedaCaja>();
	private Set<TransaccionCajaCaja> transaccionesCajaCajaOrigen = new HashSet<TransaccionCajaCaja>();
	private Set<TransaccionCajaCaja> transaccionesCajaCajaDestino = new HashSet<TransaccionCajaCaja>();
	private Set<PendienteCaja> transaccionesPendienteCaja = new HashSet<PendienteCaja>();
	private Set<TransaccionCliente> transaccionesCliente = new HashSet<TransaccionCliente>();

	public HistorialCaja() {
		// TODO Auto-generated constructor stub
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCaja")
	public Set<TransaccionBovedaCaja> getTransaccionesBovedaCaja() {
		return transaccionesBovedaCaja;
	}

	public void setTransaccionesBovedaCaja(Set<TransaccionBovedaCaja> transaccionesBovedaCaja) {
		this.transaccionesBovedaCaja = transaccionesBovedaCaja;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCaja")
	public Set<PendienteCaja> getTransaccionesPendienteCaja() {
		return transaccionesPendienteCaja;
	}

	public void setTransaccionesPendienteCaja(Set<PendienteCaja> transaccionesPendienteCaja) {
		this.transaccionesPendienteCaja = transaccionesPendienteCaja;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCaja")
	public Set<TransaccionCliente> getTransaccionesCliente() {
		return transaccionesCliente;
	}

	public void setTransaccionesCliente(Set<TransaccionCliente> transaccionesCliente) {
		this.transaccionesCliente = transaccionesCliente;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCajaOrigen")
	public Set<TransaccionCajaCaja> getTransaccionesCajaCajaOrigen() {
		return transaccionesCajaCajaOrigen;
	}

	public void setTransaccionesCajaCajaOrigen(Set<TransaccionCajaCaja> transaccionesCajaCajaOrigen) {
		this.transaccionesCajaCajaOrigen = transaccionesCajaCajaOrigen;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCajaDestino")
	public Set<TransaccionCajaCaja> getTransaccionesCajaCajaDestino() {
		return transaccionesCajaCajaDestino;
	}

	public void setTransaccionesCajaCajaDestino(Set<TransaccionCajaCaja> transaccionesCajaCajaDestino) {
		this.transaccionesCajaCajaDestino = transaccionesCajaCajaDestino;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((caja == null) ? 0 : caja.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof HistorialCaja))
			return false;
		HistorialCaja other = (HistorialCaja) obj;
		if (caja == null) {
			if (other.caja != null)
				return false;
		} else if (!caja.equals(other.caja))
			return false;
		return true;
	}

}
