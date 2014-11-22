package org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities;

import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="BOVEDA", indexes = { @Index(columnList = "id") })
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@NamedQueries({ @NamedQuery(name = BovedaEntity.findAllByIdAgencia, query = "SELECT b FROM BovedaEntity b INNER JOIN b.agencia a WHERE a.id = :idAgencia") })
public class BovedaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String base = "org.softgreen.organizacion.entity.Boveda.";
	public static final String findAllByIdAgencia = base + "findAllByIdAgencia";

	private Integer id;
	private String moneda;
	private String denominacion;
	private boolean abierto;
	private boolean estadoMovimiento;
	private boolean estado;

	private AgenciaEntity agencia;

	private Set<HistorialBovedaEntity> historiales = new HashSet<HistorialBovedaEntity>();
	private Set<BovedaCajaEntity> bovedaCajas = new HashSet<BovedaCajaEntity>();

	private Timestamp optlk;

	@Id
	@GeneratedValue(generator = "SgGenericGenerator")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	@Size(min = 3, max = 3)
	@NotBlank
	@NotEmpty
	@NaturalId
	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	@NotNull
	@NaturalId
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public AgenciaEntity getAgencia() {
		return agencia;
	}

	public void setAgencia(AgenciaEntity agencia) {
		this.agencia = agencia;
	}

	@NotNull
	@Size(min = 1, max = 40)
	@NotBlank
	@NotEmpty
	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	@NotNull
	@Type(type = "org.hibernate.type.TrueFalseType")
	public boolean isAbierto() {
		return abierto;
	}

	public void setAbierto(boolean abierto) {
		this.abierto = abierto;
	}

	@NotNull
	@Type(type = "org.hibernate.type.TrueFalseType")
	public boolean isEstadoMovimiento() {
		return estadoMovimiento;
	}

	public void setEstadoMovimiento(boolean estadoMovimiento) {
		this.estadoMovimiento = estadoMovimiento;
	}

	@NotNull
	@Type(type = "org.hibernate.type.TrueFalseType")
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "boveda")
	public Set<HistorialBovedaEntity> getHistoriales() {
		return historiales;
	}

	public void setHistoriales(Set<HistorialBovedaEntity> historiales) {
		this.historiales = historiales;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "boveda")
	public Set<BovedaCajaEntity> getBovedaCajas() {
		return bovedaCajas;
	}

	public void setBovedaCajas(Set<BovedaCajaEntity> bovedaCajas) {
		this.bovedaCajas = bovedaCajas;
	}

	@XmlTransient
	@Version
	public Timestamp getOptlk() {
		return optlk;
	}

	public void setOptlk(Timestamp optlk) {
		this.optlk = optlk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agencia == null) ? 0 : agencia.hashCode());
		result = prime * result + ((moneda == null) ? 0 : moneda.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BovedaEntity))
			return false;
		BovedaEntity other = (BovedaEntity) obj;
		if (agencia == null) {
			if (other.agencia != null)
				return false;
		} else if (!agencia.equals(other.agencia))
			return false;
		if (moneda == null) {
			if (other.moneda != null)
				return false;
		} else if (!moneda.equals(other.moneda))
			return false;
		return true;
	}

}
