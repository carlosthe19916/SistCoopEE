package org.softgreen.sistcoop.socio.ejb.models.jpa.entities;

// Generated 02-may-2014 11:48:28 by Hibernate Tools 4.0.0

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Beneficiario generated by hbm2java
 */
@Entity
@Table(name = "BENEFICIARIO", indexes = { @Index(columnList = "id") })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("beneficiario")
public class Beneficiario implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private BigDecimal porcentajeBeneficio;

	private String tipoDocumento;
	private String numeroDocumento;

	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;

	public Beneficiario() {
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
	@Size(min = 1, max = 20)
	@NotBlank
	@NotEmpty
	@Column(name = "TIPO_DOCUMENTO")
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	@NotNull
	@Size(min = 1, max = 20)
	@NotEmpty
	@NotBlank
	@Column(name = "NUMERO_DOCUMENTO")
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	@NotNull
	@Size(min = 1, max = 50)
	@NotEmpty
	@NotBlank
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	@NotNull
	@Size(min = 1, max = 50)
	@NotEmpty
	@NotBlank
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	@NotNull
	@Size(min = 1, max = 70)
	@NotEmpty
	@NotBlank
	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	@NotNull
	@Min(value = 0)
	@Max(value = 100)
	@DecimalMin(value = "0")
	@DecimalMax(value = "100")
	@Digits(integer = 3, fraction = 2)
	public BigDecimal getPorcentajeBeneficio() {
		return porcentajeBeneficio;
	}

	public void setPorcentajeBeneficio(BigDecimal porcentajeBeneficio) {
		this.porcentajeBeneficio = porcentajeBeneficio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((apellidoPaterno == null) ? 0 : apellidoPaterno.hashCode());
		result = prime * result
				+ ((apellidoMaterno == null) ? 0 : apellidoMaterno.hashCode());
		result = prime * result + ((nombres == null) ? 0 : nombres.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Beneficiario))
			return false;
		Beneficiario other = (Beneficiario) obj;
		if (apellidoPaterno == null) {
			if (other.getApellidoPaterno() != null)
				return false;
		} else if (!apellidoPaterno.equals(other.getApellidoPaterno()))
			return false;
		if (apellidoMaterno == null) {
			if (other.getApellidoMaterno() != null)
				return false;
		} else if (!apellidoMaterno.equals(other.getApellidoMaterno()))
			return false;
		if (nombres == null) {
			if (other.getNombres() != null)
				return false;
		} else if (!nombres.equals(other.getNombres()))
			return false;
		return true;
	}

}
