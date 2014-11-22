package org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities;

// Generated 02-may-2014 11:48:28 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * TransaccionCajaCaja generated by hbm2java
 */
@Entity
@Table(name="TRANSACCION_CAJA_CAJA", indexes = { @Index(columnList = "id") })
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TransaccionCajaCajaEntity extends TransaccionInternaEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String moneda;
	private HistorialCajaEntity historialCajaDestino;
	private HistorialCajaEntity historialCajaOrigen;
	private BigDecimal saldoDisponibleOrigen;
	private BigDecimal saldoDisponibleDestino;

	private BigDecimal monto;

	private Set<DetalleTransaccionCajaCajaEntity> detalle = new HashSet<DetalleTransaccionCajaCajaEntity>();

	public TransaccionCajaCajaEntity() {
	}

	@Id
	@GeneratedValue(generator = "SgGenericGenerator")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@Size(min = 3, max = 3)
	@NotBlank
	@NotEmpty
	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public HistorialCajaEntity getHistorialCajaDestino() {
		return historialCajaDestino;
	}

	public void setHistorialCajaDestino(HistorialCajaEntity historialCajaDestino) {
		this.historialCajaDestino = historialCajaDestino;
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public HistorialCajaEntity getHistorialCajaOrigen() {
		return historialCajaOrigen;
	}

	public void setHistorialCajaOrigen(HistorialCajaEntity historialCajaOrigen) {
		this.historialCajaOrigen = historialCajaOrigen;
	}

	@NotNull
	@Min(value = 0)
	@DecimalMin(value = "0")
	@Digits(integer = 18, fraction = 2)
	public BigDecimal getSaldoDisponibleOrigen() {
		return saldoDisponibleOrigen;
	}

	public void setSaldoDisponibleOrigen(BigDecimal saldoDisponibleOrigen) {
		this.saldoDisponibleOrigen = saldoDisponibleOrigen;
	}

	@NotNull
	@Min(value = 0)
	@DecimalMin(value = "0")
	@Digits(integer = 18, fraction = 2)
	public BigDecimal getSaldoDisponibleDestino() {
		return saldoDisponibleDestino;
	}

	public void setSaldoDisponibleDestino(BigDecimal saldoDisponibleDestino) {
		this.saldoDisponibleDestino = saldoDisponibleDestino;
	}

	@NotNull
	@Min(value = 0)
	@DecimalMin(value = "0")
	@Digits(integer = 18, fraction = 2)
	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "transaccionCajaCaja")
	public Set<DetalleTransaccionCajaCajaEntity> getDetalle() {
		return detalle;
	}

	public void setDetalle(Set<DetalleTransaccionCajaCajaEntity> detalle) {
		this.detalle = detalle;
	}

}
