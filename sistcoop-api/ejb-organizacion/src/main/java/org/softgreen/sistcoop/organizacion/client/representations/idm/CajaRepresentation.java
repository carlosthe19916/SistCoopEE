package org.softgreen.sistcoop.organizacion.client.representations.idm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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

	private Map<String, BigDecimal> bovedas;
	private List<TrabajadorRepresentation> trabajadores;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public boolean isAbierto() {
		return abierto;
	}

	public void setAbierto(boolean abierto) {
		this.abierto = abierto;
	}

	public boolean isEstadoMovimiento() {
		return estadoMovimiento;
	}

	public void setEstadoMovimiento(boolean estadoMovimiento) {
		this.estadoMovimiento = estadoMovimiento;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Map<String, BigDecimal> getBovedas() {
		return bovedas;
	}

	public void setBovedas(Map<String, BigDecimal> bovedas) {
		this.bovedas = bovedas;
	}

	public List<TrabajadorRepresentation> getTrabajadores() {
		return trabajadores;
	}

	public void setTrabajadores(List<TrabajadorRepresentation> trabajadores) {
		this.trabajadores = trabajadores;
	}

}
