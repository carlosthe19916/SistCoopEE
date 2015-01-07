package org.softgreen.sistcoop.organizacion.client.representations.idm;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "caja")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CajaRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String denominacion;
	private boolean abierto;
	private boolean estadoMovimiento;
	private boolean estado;

	private AgenciaRepresentation agencia;

	private List<BovedaRepresentation> bovedas;
	private List<TrabajadorRepresentation> trabajadores;

	@XmlAttribute
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlAttribute
	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	@XmlAttribute
	public boolean isAbierto() {
		return abierto;
	}

	public void setAbierto(boolean abierto) {
		this.abierto = abierto;
	}

	@XmlAttribute
	public boolean isEstadoMovimiento() {
		return estadoMovimiento;
	}

	public void setEstadoMovimiento(boolean estadoMovimiento) {
		this.estadoMovimiento = estadoMovimiento;
	}

	@XmlAttribute
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@XmlElement
	public AgenciaRepresentation getAgencia() {
		return agencia;
	}

	public void setAgencia(AgenciaRepresentation agencia) {
		this.agencia = agencia;
	}

	@XmlElement
	public List<BovedaRepresentation> getBovedas() {
		return bovedas;
	}

	public void setBovedas(List<BovedaRepresentation> bovedas) {
		this.bovedas = bovedas;
	}

	@XmlElement
	public List<TrabajadorRepresentation> getTrabajadores() {
		return trabajadores;
	}

	public void setTrabajadores(List<TrabajadorRepresentation> trabajadores) {
		this.trabajadores = trabajadores;
	}

}
