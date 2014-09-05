package org.softgreen.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.softgreen.entity.type.TipoEmpresa;

public class PersonaJuridica extends Persona {

	private String razonSocial;
	private String nombreComercial;
	private Date fechaConstitucion;
	private String actividadPrincipal;
	private TipoEmpresa tipoEmpresa;
	private Integer finLucro;

	private PersonaNatural representanteLegal;
	private Set<PersonaNatural> accionistas = new HashSet<PersonaNatural>(0);

	public PersonaJuridica() {
		super();
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

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public Integer getFinLucro() {
		return finLucro;
	}

	public void setFinLucro(Integer finLucro) {
		this.finLucro = finLucro;
	}

	public PersonaNatural getRepresentanteLegal() {
		return representanteLegal;
	}

	public void setRepresentanteLegal(PersonaNatural representanteLegal) {
		this.representanteLegal = representanteLegal;
	}

	public Set<PersonaNatural> getAccionistas() {
		return accionistas;
	}

	public void setAccionistas(Set<PersonaNatural> accionistas) {
		this.accionistas = accionistas;
	}
}
