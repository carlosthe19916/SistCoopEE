package org.softgreen.sistcoop.persona.ejb.models.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
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

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.softgreen.sistcoop.persona.clien.enums.EstadoCivil;
import org.softgreen.sistcoop.persona.clien.enums.Sexo;

@Entity
@Table(indexes = { @Index(columnList = "id") })
@NamedQueries({
		@NamedQuery(name = PersonaNaturalEntity.findAll, query = "SELECT p FROM PersonaNaturalEntity p ORDER BY p.apellidoPaterno, p.apellidoMaterno, p.nombres, p.id"),
		@NamedQuery(name = PersonaNaturalEntity.findByTipoAndNumeroDocumento, query = "SELECT p FROM PersonaNaturalEntity p WHERE p.tipoDocumento.abreviatura = :tipoDocumento AND p.numeroDocumento = :numeroDocumento "),
		@NamedQuery(name = PersonaNaturalEntity.findByFilterText, query = "SELECT p FROM PersonaNaturalEntity p WHERE p.numeroDocumento LIKE :filterText OR UPPER(CONCAT(p.apellidoPaterno,' ', p.apellidoMaterno,' ',p.nombres)) LIKE :filterText ORDER BY p.apellidoPaterno, p.apellidoMaterno, p.nombres, p.id"),
		@NamedQuery(name = PersonaNaturalEntity.count, query = "select count(u) from PersonaNaturalEntity u") })
public class PersonaNaturalEntity extends PersonaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String base = "org.softgreen.persona.entity.PersonaNatural";
	public final static String findAll = base + "findAll";
	public final static String findByTipoAndNumeroDocumento = base + "findByTipoAndNumeroDocumento";
	public final static String findByFilterText = base + "findByFilterText";
	public final static String count = base + "count";

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

	private Set<AccionistaEntity> accionistas = new HashSet<AccionistaEntity>();

	public PersonaNaturalEntity() {
		super();
	}

	public PersonaNaturalEntity(Long id) {
		super();
		this.id = id;
	}

	public PersonaNaturalEntity(TipoDocumentoEntity tipoDocumento,
			String numeroDocumento) {
		super(tipoDocumento, numeroDocumento);
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
	@Column(nullable = false)
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
	@Column(nullable = false)
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
	@Column(nullable = false)
	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	@Size(min = 0, max = 70)
	@Column(nullable = true)
	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	@URL
	@Column(nullable = true)
	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	@URL
	@Column(nullable = true)
	public String getUrlFirma() {
		return urlFirma;
	}

	public void setUrlFirma(String urlFirma) {
		this.urlFirma = urlFirma;
	}

	@OneToMany(mappedBy = "personaNatural", fetch = FetchType.LAZY)
	public Set<AccionistaEntity> getAccionistas() {
		return accionistas;
	}

	public void setAccionistas(Set<AccionistaEntity> accionistas) {
		this.accionistas = accionistas;
	}

}
