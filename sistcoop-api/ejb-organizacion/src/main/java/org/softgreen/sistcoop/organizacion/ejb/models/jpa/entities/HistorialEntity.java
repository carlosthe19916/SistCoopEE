package org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Type;

@Entity
@Table(name="HISTORIAL", indexes = { @Index(columnList = "id") })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class HistorialEntity {
	
	protected Long id;
	protected Date fechaApertura;
	protected Date fechaCierre;
	protected Date horaApertura;
	protected Date horaCierre;
	protected boolean estado;

	private Set<DetalleHistorialEntity> detalle = new HashSet<DetalleHistorialEntity>();

	private Timestamp optlk;

	@Id
	@GeneratedValue(generator = "SgGenericGenerator")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	@Temporal(TemporalType.DATE)
	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	public Date getHoraApertura() {
		return horaApertura;
	}

	public void setHoraApertura(Date horaApertura) {
		this.horaApertura = horaApertura;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getHoraCierre() {
		return horaCierre;
	}

	public void setHoraCierre(Date horaCierre) {
		this.horaCierre = horaCierre;
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
	@OneToMany(mappedBy = "historial", fetch = FetchType.LAZY)
	public Set<DetalleHistorialEntity> getDetalle() {
		return detalle;
	}

	public void setDetalle(Set<DetalleHistorialEntity> detalle) {
		this.detalle = detalle;
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
		result = prime * result + ((fechaApertura == null) ? 0 : fechaApertura.hashCode());
		result = prime * result + ((horaApertura == null) ? 0 : horaApertura.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof HistorialEntity))
			return false;
		HistorialEntity other = (HistorialEntity) obj;
		if (fechaApertura == null) {
			if (other.fechaApertura != null)
				return false;
		} else if (!fechaApertura.equals(other.fechaApertura))
			return false;
		if (horaApertura == null) {
			if (other.horaApertura != null)
				return false;
		} else if (!horaApertura.equals(other.horaApertura))
			return false;
		return true;
	}

}
