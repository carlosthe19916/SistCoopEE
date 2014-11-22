package org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name="TRABAJADOR_CAJA", indexes = { @Index(columnList = "id") })
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TrabajadorCajaEntity {

	private Integer id;
	private TrabajadorEntity trabajador;
	private CajaEntity caja;

	private Timestamp optlk;

	public TrabajadorCajaEntity() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(generator = "SgGenericGenerator")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	@NaturalId
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public TrabajadorEntity getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(TrabajadorEntity trabajador) {
		this.trabajador = trabajador;
	}

	@NotNull
	@NaturalId
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public CajaEntity getCaja() {
		return caja;
	}

	public void setCaja(CajaEntity caja) {
		this.caja = caja;
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
		result = prime * result + ((caja == null) ? 0 : caja.hashCode());
		result = prime * result + ((trabajador == null) ? 0 : trabajador.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TrabajadorCajaEntity))
			return false;
		TrabajadorCajaEntity other = (TrabajadorCajaEntity) obj;
		if (caja == null) {
			if (other.caja != null)
				return false;
		} else if (!caja.equals(other.caja))
			return false;
		if (trabajador == null) {
			if (other.trabajador != null)
				return false;
		} else if (!trabajador.equals(other.trabajador))
			return false;
		return true;
	}

}
