package org.softgreen.ubigeo.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@NamedQuery(name = Country.findAll, query = "Select p from Country p")
@NamedQueries(value = { @NamedQuery(name = Country.findByAlpha2Code, query = "Select p from Country p WHERE p.alpha2Code = :code"), @NamedQuery(name = Country.findByAlpha3Code, query = "Select p from Country p WHERE p.alpha3Code = :code"), @NamedQuery(name = Country.findByNumericCode, query = "Select p from Country p WHERE p.numericCode = :code") })
public class Country implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String base = "org.softgreen.ubigeo.entity.Pais";
	public final static String findAll = base + "findAll";
	public final static String findByAlpha2Code = base + "findByAlpha2Code";
	public final static String findByAlpha3Code = base + "findByAlpha3Code";
	public final static String findByNumericCode = base + "findByNumericCode";

	private Integer id;

	private String alpha2Code;
	private String shortName;
	private String shortNameLowerCase;
	private String fullName;
	private String alpha3Code;
	private String numericCode;
	private String remarks;
	private boolean independent;
	private String territoryName;
	private String status;

	private Currency currency;
	private Set<SubDivisionCategory> subDivisionCategories = new HashSet<SubDivisionCategory>();

	private Timestamp version;

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
	@Column(unique = true)
	public String getAlpha2Code() {
		return alpha2Code;
	}

	public void setAlpha2Code(String alpha2Code) {
		this.alpha2Code = alpha2Code;
	}

	@NotNull
	@Size(min = 1, max = 30)
	@NotBlank
	@NotEmpty
	@Column(unique = true)
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@NotNull
	@Size(min = 1, max = 30)
	@NotBlank
	@NotEmpty
	@Column(unique = true)
	public String getShortNameLowerCase() {
		return shortNameLowerCase;
	}

	public void setShortNameLowerCase(String shortNameLowerCase) {
		this.shortNameLowerCase = shortNameLowerCase;
	}

	@NotNull
	@Size(min = 1, max = 150)
	@NotBlank
	@NotEmpty
	@Column(unique = true)
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@NotNull
	@Size(min = 3, max = 3)
	@NotBlank
	@NotEmpty
	@Column(unique = true)
	public String getAlpha3Code() {
		return alpha3Code;
	}

	public void setAlpha3Code(String alpha3Code) {
		this.alpha3Code = alpha3Code;
	}

	@NotNull
	@Size(min = 1, max = 5)
	@NotBlank
	@NotEmpty
	@Column(unique = true)
	public String getNumericCode() {
		return numericCode;
	}

	public void setNumericCode(String numericCode) {
		this.numericCode = numericCode;
	}

	@NotNull
	@Size(min = 0, max = 300)
	@NotBlank
	@NotEmpty
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@NotNull
	@Type(type = "org.hibernate.type.TrueFalseType")
	@Column(name = "ESTADO")
	public boolean isIndependent() {
		return independent;
	}

	public void setIndependent(boolean independent) {
		this.independent = independent;
	}

	@NotNull
	@Size(min = 0, max = 300)
	@NotBlank
	@NotEmpty
	public String getTerritoryName() {
		return territoryName;
	}

	public void setTerritoryName(String territoryName) {
		this.territoryName = territoryName;
	}

	@NotNull
	@Size(min = 0, max = 100)
	@NotBlank
	@NotEmpty
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}	

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn
	public Set<SubDivisionCategory> getSubDivisionCategories() {
		return subDivisionCategories;
	}

	public void setSubDivisionCategories(Set<SubDivisionCategory> subDivisionCategories) {
		this.subDivisionCategories = subDivisionCategories;
	}

	@Version
	public Timestamp getVersion() {
		return version;
	}

	public void setVersion(Timestamp version) {
		this.version = version;
	}
}
