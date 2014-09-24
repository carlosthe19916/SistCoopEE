package org.softgreen.sistcoop.persona.representations.idm;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PersonaJuridicaRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String codigoPais;
	private String tipoDocumento;
	private String numeroDocumento;

	private String razonSocial;
	private String nombreComercial;
	private Date fechaConstitucion;
	private String actividadPrincipal;
	private String tipoEmpresa;
	private boolean finLucro;

	private String ubigeo;
	private String direccion;
	private String referencia;
	private String telefono;
	private String celular;
	private String email;

	private Long idRepresentanteLegal;
	private String tipoDocumentoRepresentanteLegal;
	private String numeroDocumentoRepresentanteLegal;

	private Set<Long> accionistas = new HashSet<Long>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoPais() {
		return codigoPais;
	}

	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public Date getFechaConstitucion() {
		return fechaConstitucion;
	}

	public void setFechaConstitucion(Date fechaConstitucion) {
		this.fechaConstitucion = fechaConstitucion;
	}

	public String getActividadPrincipal() {
		return actividadPrincipal;
	}

	public void setActividadPrincipal(String actividadPrincipal) {
		this.actividadPrincipal = actividadPrincipal;
	}

	public String getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(String tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public boolean isFinLucro() {
		return finLucro;
	}

	public void setFinLucro(boolean finLucro) {
		this.finLucro = finLucro;
	}

	public String getUbigeo() {
		return ubigeo;
	}

	public void setUbigeo(String ubigeo) {
		this.ubigeo = ubigeo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getIdRepresentanteLegal() {
		return idRepresentanteLegal;
	}

	public void setIdRepresentanteLegal(Long idRepresentanteLegal) {
		this.idRepresentanteLegal = idRepresentanteLegal;
	}

	public String getTipoDocumentoRepresentanteLegal() {
		return tipoDocumentoRepresentanteLegal;
	}

	public void setTipoDocumentoRepresentanteLegal(
			String tipoDocumentoRepresentanteLegal) {
		this.tipoDocumentoRepresentanteLegal = tipoDocumentoRepresentanteLegal;
	}

	public String getNumeroDocumentoRepresentanteLegal() {
		return numeroDocumentoRepresentanteLegal;
	}

	public void setNumeroDocumentoRepresentanteLegal(
			String numeroDocumentoRepresentanteLegal) {
		this.numeroDocumentoRepresentanteLegal = numeroDocumentoRepresentanteLegal;
	}

	public Set<Long> getAccionistas() {
		return accionistas;
	}

	public void setAccionistas(Set<Long> accionistas) {
		this.accionistas = accionistas;
	}
}
