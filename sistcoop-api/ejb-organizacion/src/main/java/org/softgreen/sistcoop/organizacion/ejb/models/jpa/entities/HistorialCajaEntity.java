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
@DiscriminatorValue("caja")
@NamedQueries({ 
	@NamedQuery(name = HistorialCajaEntity.findByHistorialActivo, query = "SELECT h FROM HistorialCajaEntity h INNER JOIN h.caja c WHERE c.id = :idCaja AND h.estado = true"), 
	@NamedQuery(name = HistorialCajaEntity.findByHistorialDateRange, query = "SELECT h FROM HistorialCajaEntity h INNER JOIN h.caja c WHERE c.id = :idCaja AND h.fechaApertura BETWEEN :desde AND :hasta AND h.estado = false ORDER BY h.horaApertura DESC"),
	@NamedQuery(name = HistorialCajaEntity.findByEstado, query = "SELECT s FROM HistorialCajaEntity s WHERE s.caja.id = :idCaja AND s.estado = :estado")})
public class HistorialCajaEntity extends HistorialEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String base = "org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialCajaEntity.";
	public final static String findByHistorialActivo = base+"findByHistorialActivo";
	public final static String findByHistorialDateRange = base+"findByHistorialDateRange";
	public final static String findByEstado = base+"findByEstado";
	
	private CajaEntity caja;
	private Set<TransaccionBovedaCajaEntity> transaccionesBovedaCaja = new HashSet<TransaccionBovedaCajaEntity>();
	private Set<TransaccionCajaCajaEntity> transaccionesCajaCajaOrigen = new HashSet<TransaccionCajaCajaEntity>();
	private Set<TransaccionCajaCajaEntity> transaccionesCajaCajaDestino = new HashSet<TransaccionCajaCajaEntity>();
	private Set<PendienteCajaEntity> transaccionesPendienteCaja = new HashSet<PendienteCajaEntity>();
	private Set<TransaccionClienteEntity> transaccionesCliente = new HashSet<TransaccionClienteEntity>();

	public HistorialCajaEntity() {
		// TODO Auto-generated constructor stub
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public CajaEntity getCaja() {
		return caja;
	}

	public void setCaja(CajaEntity caja) {
		this.caja = caja;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCaja")
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCaja")
	public Set<TransaccionClienteEntity> getTransaccionesCliente() {
		return transaccionesCliente;
	}

	public void setTransaccionesCliente(Set<TransaccionClienteEntity> transaccionesCliente) {
		this.transaccionesCliente = transaccionesCliente;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCajaOrigen")
	public Set<TransaccionCajaCajaEntity> getTransaccionesCajaCajaOrigen() {
		return transaccionesCajaCajaOrigen;
	}

	public void setTransaccionesCajaCajaOrigen(Set<TransaccionCajaCajaEntity> transaccionesCajaCajaOrigen) {
		this.transaccionesCajaCajaOrigen = transaccionesCajaCajaOrigen;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCajaDestino")
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
		result = prime * result + ((caja == null) ? 0 : caja.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof HistorialCajaEntity))
			return false;
		HistorialCajaEntity other = (HistorialCajaEntity) obj;
		if (caja == null) {
			if (other.caja != null)
				return false;
		} else if (!caja.equals(other.caja))
			return false;
		return true;
	}

}
