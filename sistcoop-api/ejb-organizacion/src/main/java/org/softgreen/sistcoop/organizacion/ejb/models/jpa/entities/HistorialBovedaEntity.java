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
@DiscriminatorValue("boveda")
@NamedQueries(value = {		
		@NamedQuery(name = HistorialBovedaEntity.findByEstado, query = "SELECT s FROM HistorialBovedaEntity s WHERE s.boveda.id = :idBoveda AND s.estado = :estado")})
public class HistorialBovedaEntity extends HistorialEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String base = "org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialBovedaEntity.";	
	public final static String findByEstado = base + "findByEstado";	
	
	private BovedaEntity boveda;
	private Set<TransaccionBovedaCajaEntity> transaccionesBovedaCaja = new HashSet<TransaccionBovedaCajaEntity>();
	private Set<TransaccionBovedaEntidadEntity> transaccionesBovedaEntidad = new HashSet<TransaccionBovedaEntidadEntity>();

	public HistorialBovedaEntity() {
		// TODO Auto-generated constructor stub
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public BovedaEntity getBoveda() {
		return boveda;
	}

	public void setBoveda(BovedaEntity boveda) {
		this.boveda = boveda;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialBoveda")
	public Set<TransaccionBovedaCajaEntity> getTransaccionesBovedaCaja() {
		return transaccionesBovedaCaja;
	}

	public void setTransaccionesBovedaCaja(Set<TransaccionBovedaCajaEntity> transaccionesBovedaCaja) {
		this.transaccionesBovedaCaja = transaccionesBovedaCaja;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialBoveda")
	public Set<TransaccionBovedaEntidadEntity> getTransaccionesBovedaEntidad() {
		return transaccionesBovedaEntidad;
	}

	public void setTransaccionesBovedaEntidad(Set<TransaccionBovedaEntidadEntity> transaccionesBovedaEntidad) {
		this.transaccionesBovedaEntidad = transaccionesBovedaEntidad;
	}
}
