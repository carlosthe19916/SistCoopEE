package org.softgreen.ubigeo.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.NaturalId;

@Entity
@Table
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ExchangeRate {

	public final static String base = "org.softgreen.ubigeo.entity.TasaCambio";
	public final static String findByCurrencyOriginDestiny = base + "findByCurrencyOriginDestiny";

	private Long id;
	private Currency currencyOrigin;
	private Currency currencyDestiny;
	private Date fecha;
	private BigDecimal valor;

	private Timestamp version;

	public ExchangeRate() {
		// TODO Auto-generated constructor stub
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
	@NaturalId
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public Currency getCurrencyOrigin() {
		return currencyOrigin;
	}

	public void setCurrencyOrigin(Currency currencyOrigin) {
		this.currencyOrigin = currencyOrigin;
	}

	@NotNull
	@NaturalId
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public Currency getCurrencyDestiny() {
		return currencyDestiny;
	}

	public void setCurrencyDestiny(Currency currencyDestiny) {
		this.currencyDestiny = currencyDestiny;
	}

	@NotNull
	@NaturalId
	@Temporal(TemporalType.DATE)
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@NotNull
	@Min(value = 0)
	@Max(value = 100)
	@DecimalMin(value = "0")
	@DecimalMax(value = "99")
	@Digits(integer = 2, fraction = 3)
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Version
	public Timestamp getVersion() {
		return this.version;
	}

	public void setVersion(Timestamp version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((currencyDestiny == null) ? 0 : currencyDestiny.hashCode());
		result = prime * result + ((currencyOrigin == null) ? 0 : currencyOrigin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ExchangeRate))
			return false;
		ExchangeRate other = (ExchangeRate) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (currencyDestiny == null) {
			if (other.currencyDestiny != null)
				return false;
		} else if (!currencyDestiny.equals(other.currencyDestiny))
			return false;
		if (currencyOrigin == null) {
			if (other.currencyOrigin != null)
				return false;
		} else if (!currencyOrigin.equals(other.currencyOrigin))
			return false;
		return true;
	}

}
