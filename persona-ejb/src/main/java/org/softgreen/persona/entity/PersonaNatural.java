package org.softgreen.persona.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
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

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.softgreen.persona.entity.type.EstadoCivil;
import org.softgreen.persona.entity.type.Sexo;

@Entity
@Table(indexes = { @Index(columnList = "id") })
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@NamedQueries({ 
		@NamedQuery(name = PersonaNatural.findAll, query = "SELECT p FROM PersonaNatural p ORDER BY p.apellidoPaterno, p.apellidoMaterno, p.nombres, p.idPersonaNatural"), 
		@NamedQuery(name = PersonaNatural.findByTipoAndNumeroDocumento, query = "SELECT p FROM PersonaNatural p WHERE p.tipoDocumento = :tipoDocumento AND p.numeroDocumento = :numeroDocumento "),
		@NamedQuery(name = PersonaNatural.findByFilterText, query = "SELECT p FROM PersonaNatural p WHERE p.numeroDocumento LIKE :filterText OR UPPER(CONCAT(p.apellidoPaterno,' ', p.apellidoMaterno,' ',p.nombres)) LIKE :filterText ORDER BY p.apellidoPaterno, p.apellidoMaterno, p.nombres, p.idPersonaNatural") })
public class PersonaNatural extends Persona implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String base = "org.softgreen.persona.entity.PersonaNatural";
	public final static String findAll = base + "findAll";
	public final static String findByTipoAndNumeroDocumento = base + "findByTipoAndNumeroDocumento";
	public final static String findByFilterText = base + "findByFilterText";

	private Long id;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	private Date fechaNacimiento;
	private Sexo sexo;
	private EstadoCivil estadoCivil;
	private String ocupacion;
	private String urlFoto;
	private String urlFirma;

	private Set<Accionista> accionistas = new HashSet<Accionista>();

	public PersonaNatural() {
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
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.DATE)
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@NotNull
	@Size(min = 1, max = 50)
	@NotEmpty
	@NotBlank
	@Enumerated(EnumType.STRING)
	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	@NotNull
	@Size(min = 1, max = 50)
	@NotEmpty
	@NotBlank
	@Enumerated(EnumType.STRING)
	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	@Size(min = 0, max = 70)
	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	@URL
	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	@URL
	public String getUrlFirma() {
		return urlFirma;
	}

	public void setUrlFirma(String urlFirma) {
		this.urlFirma = urlFirma;
	}

	@XmlTransient
	@OneToMany(mappedBy = "personaNatural", fetch = FetchType.LAZY)
	public Set<Accionista> getAccionistas() {
		return accionistas;
	}

	public void setAccionistas(Set<Accionista> accionistas) {
		this.accionistas = accionistas;
	}

}
