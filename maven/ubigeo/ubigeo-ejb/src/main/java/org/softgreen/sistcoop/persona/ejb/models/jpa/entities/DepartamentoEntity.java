package org.softgreen.sistcoop.persona.ejb.models.jpa.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DepartamentoEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String codigo;
	private String denominacion;

	private Set<ProvinciaEntity> provincias = new HashSet<ProvinciaEntity>();

	public DepartamentoEntity() {
		// TODO Auto-generated constructor stub
	}

	@XmlTransient
	@Id
	@GeneratedValue(generator = "SgGenericGenerator")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	@Size(min = 2, max = 2)
	@NotBlank
	@NotEmpty
	@NaturalId
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Size(min = 0, max = 100)
	@Column(nullable = true)
	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "departamento")
	public Set<ProvinciaEntity> getProvincias() {
		return provincias;
	}

	public void setProvincias(Set<ProvinciaEntity> provincias) {
		this.provincias = provincias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DepartamentoEntity))
			return false;
		DepartamentoEntity other = (DepartamentoEntity) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
