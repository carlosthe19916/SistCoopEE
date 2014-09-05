package org.softgreen.persona.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.softgreen.entity.type.TipoEmpresa;

@Entity
@Table(indexes = { @Index(columnList = "id") })
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PersonaJuridica extends Persona {

	private Long id;
	private String razonSocial;
	private String nombreComercial;
	private Date fechaConstitucion;
	private String actividadPrincipal;
	private TipoEmpresa tipoEmpresa;
	private boolean finLucro;

	private PersonaNatural representanteLegal;
	private Set<Accionista> accionistas = new HashSet<Accionista>();

	public PersonaJuridica() {
		super();
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
	@Size(min = 1, max = 70)
	@NotEmpty
	@NotBlank
	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	@NotNull
	@Size(min = 0, max = 50)
	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.DATE)
	public Date getFechaConstitucion() {
		return fechaConstitucion;
	}

	public void setFechaConstitucion(Date fechaConstitucion) {
		this.fechaConstitucion = fechaConstitucion;
	}

	@NotNull
	@Size(min = 0, max = 70)
	public String getActividadPrincipal() {
		return actividadPrincipal;
	}

	public void setActividadPrincipal(String actividadPrincipal) {
		this.actividadPrincipal = actividadPrincipal;
	}

	@NotNull
	@Size(min = 1, max = 50)
	@NotEmpty
	@NotBlank
	@Enumerated(EnumType.STRING)
	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	@NotNull
	@Type(type = "org.hibernate.type.TrueFalseType")
	public boolean isFinLucro() {
		return finLucro;
	}

	public void setFinLucro(boolean finLucro) {
		this.finLucro = finLucro;
	}

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public PersonaNatural getRepresentanteLegal() {
		return representanteLegal;
	}

	public void setRepresentanteLegal(PersonaNatural representanteLegal) {
		this.representanteLegal = representanteLegal;
	}

	@XmlTransient
	@OneToMany(mappedBy = "personaJuridica", fetch = FetchType.LAZY)
	public Set<Accionista> getAccionistas() {
		return accionistas;
	}

	public void setAccionistas(Set<Accionista> accionistas) {
		this.accionistas = accionistas;
	}

}
