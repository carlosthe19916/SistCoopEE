package org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities;

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

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@DiscriminatorValue("bovedaCaja")
@NamedQueries({ 
	@NamedQuery(name = HistorialBovedaCajaEntity.findByHistorialActivo, query = "SELECT h FROM HistorialBovedaCajaEntity h INNER JOIN h.bovedaCaja c WHERE c.id = :idCaja AND h.estado = true"), 
	@NamedQuery(name = HistorialBovedaCajaEntity.findByHistorialDateRange, query = "SELECT h FROM HistorialBovedaCajaEntity h INNER JOIN h.bovedaCaja c WHERE c.id = :idCaja AND h.fechaApertura BETWEEN :desde AND :hasta AND h.estado = false ORDER BY h.horaApertura DESC"),
	@NamedQuery(name = HistorialBovedaCajaEntity.findByEstado, query = "SELECT s FROM HistorialBovedaCajaEntity s WHERE s.bovedaCaja.id = :idBovedaCaja AND s.estado = :estado")})
public class HistorialBovedaCajaEntity extends HistorialEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String base = "org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialCajaEntity.";
	public final static String findByHistorialActivo = base+"findByHistorialActivo";
	public final static String findByHistorialDateRange = base+"findByHistorialDateRange";
	public final static String findByEstado = base+"findByEstado";
	
	private BovedaCajaEntity bovedaCaja;
	private Set<TransaccionBovedaCajaEntity> transaccionesBovedaCaja = new HashSet<TransaccionBovedaCajaEntity>();
	private Set<TransaccionCajaCajaEntity> transaccionesCajaCajaOrigen = new HashSet<TransaccionCajaCajaEntity>();
	private Set<TransaccionCajaCajaEntity> transaccionesCajaCajaDestino = new HashSet<TransaccionCajaCajaEntity>();
	private Set<PendienteCajaEntity> transaccionesPendienteCaja = new HashSet<PendienteCajaEntity>();
	private Set<TransaccionClienteEntity> transaccionesCliente = new HashSet<TransaccionClienteEntity>();

	public HistorialBovedaCajaEntity() {
		// TODO Auto-generated constructor stub
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public BovedaCajaEntity getBovedaCaja() {
		return bovedaCaja;
	}

	public void setBovedaCaja(BovedaCajaEntity bovedaCaja) {
		this.bovedaCaja = bovedaCaja;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialBovedaCaja")
	public Set<TransaccionBovedaCajaEntity> getTransaccionesBovedaCaja() {
		return transaccionesBovedaCaja;
	}

	public void setTransaccionesBovedaCaja(Set<TransaccionBovedaCajaEntity> transaccionesBovedaCaja) {
		this.transaccionesBovedaCaja = transaccionesBovedaCaja;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCaja")
	public Set<PendienteCajaEntity> getTransaccionesPendienteCaja() {
		return transaccionesPendienteCaja;
	}

	public void setTransaccionesPendienteCaja(Set<PendienteCajaEntity> transaccionesPendienteCaja) {
		this.transaccionesPendienteCaja = transaccionesPendienteCaja;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialBovedaCaja")
	public Set<TransaccionClienteEntity> getTransaccionesCliente() {
		return transaccionesCliente;
	}

	public void setTransaccionesCliente(Set<TransaccionClienteEntity> transaccionesCliente) {
		this.transaccionesCliente = transaccionesCliente;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialBovedaCajaOrigen")
	public Set<TransaccionCajaCajaEntity> getTransaccionesCajaCajaOrigen() {
		return transaccionesCajaCajaOrigen;
	}

	public void setTransaccionesCajaCajaOrigen(Set<TransaccionCajaCajaEntity> transaccionesCajaCajaOrigen) {
		this.transaccionesCajaCajaOrigen = transaccionesCajaCajaOrigen;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialBovedaCajaDestino")
	public Set<TransaccionCajaCajaEntity> getTransaccionesCajaCajaDestino() {
		return transaccionesCajaCajaDestino;
	}

	public void setTransaccionesCajaCajaDestino(Set<TransaccionCajaCajaEntity> transaccionesCajaCajaDestino) {
		this.transaccionesCajaCajaDestino = transaccionesCajaCajaDestino;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bovedaCaja == null) ? 0 : bovedaCaja.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof HistorialBovedaCajaEntity))
			return false;
		HistorialBovedaCajaEntity other = (HistorialBovedaCajaEntity) obj;
		if (bovedaCaja == null) {
			if (other.bovedaCaja != null)
				return false;
		} else if (!bovedaCaja.equals(other.bovedaCaja))
			return false;
		return true;
	}

}
