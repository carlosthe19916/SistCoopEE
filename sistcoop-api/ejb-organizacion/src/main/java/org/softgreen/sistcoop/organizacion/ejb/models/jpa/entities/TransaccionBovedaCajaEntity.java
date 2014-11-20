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
 * TransaccionBovedaCaja generated by hbm2java
 */

@Entity
@Table(indexes = { @Index(columnList = "id") })
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TransaccionBovedaCajaEntity extends TransaccionInternaEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private HistorialBovedaEntity historialBoveda;
	private HistorialCajaEntity historialCaja;

	private BigDecimal saldoDisponibleOrigen;
	private BigDecimal saldoDisponibleDestino;

	private String origen;

	private Set<DetalleTransaccionBovedaCajaEntity> detalle = new HashSet<DetalleTransaccionBovedaCajaEntity>();

	public TransaccionBovedaCajaEntity() {
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public HistorialBovedaEntity getHistorialBoveda() {
		return historialBoveda;
	}

	public void setHistorialBoveda(HistorialBovedaEntity historialBoveda) {
		this.historialBoveda = historialBoveda;
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public HistorialCajaEntity getHistorialCaja() {
		return historialCaja;
	}

	public void setHistorialCaja(HistorialCajaEntity historialCaja) {
		this.historialCaja = historialCaja;
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
	@Size(min = 1, max = 10)
	@NotBlank
	@NotEmpty
	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "transaccionBovedaCaja")
	public Set<DetalleTransaccionBovedaCajaEntity> getDetalle() {
		return this.detalle;
	}

	public void setDetalle(Set<DetalleTransaccionBovedaCajaEntity> detalle) {
		this.detalle = detalle;
	}

}
