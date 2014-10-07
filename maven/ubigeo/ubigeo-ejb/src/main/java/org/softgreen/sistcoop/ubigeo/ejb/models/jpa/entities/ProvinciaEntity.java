package org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@NamedQueries({ @NamedQuery(name = ProvinciaEntity.findByCodDepartamento, query = "SELECT p FROM ProvinciaEntity p INNER JOIN p.departamento d WHERE d.codigo = :codigoDepartamento order by p.denominacion") })
public class ProvinciaEntity implements Serializable {

	public final static String findByCodDepartamento = "org.softgreen.ubigeo.entity.Provincia.findByCodDepartamento";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String codigo;
	private String denominacion;
	private DepartamentoEntity departamento;

	private Set<DistritoEntity> distritos = new HashSet<DistritoEntity>();

	public ProvinciaEntity() {
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
	@NotNull
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey)
	public DepartamentoEntity getDepartamento() {
		return departamento;
	}

	public void setDepartamento(DepartamentoEntity departamento) {
		this.departamento = departamento;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "provincia")
	public Set<DistritoEntity> getDistritos() {
		return distritos;
	}

	public void setDistritos(Set<DistritoEntity> distritos) {
		this.distritos = distritos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((departamento == null) ? 0 : departamento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProvinciaEntity))
			return false;
		ProvinciaEntity other = (ProvinciaEntity) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (departamento == null) {
			if (other.departamento != null)
				return false;
		} else if (!departamento.equals(other.departamento))
			return false;
		return true;
	}

}
