package org.softgreen.sistcoop.organizacion.client.representations.idm;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "boveda")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class BovedaRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String moneda;
	private String denominacion;
	private boolean abierto;
	private boolean estadoMovimiento;
	private boolean estado;

	private BigDecimal saldo;
	
	private AgenciaRepresentation agencia;

	@XmlAttribute
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlAttribute
	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
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

	@XmlAttribute
	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	@XmlElement	
	public AgenciaRepresentation getAgencia() {
		return agencia;
	}

	public void setAgencia(AgenciaRepresentation agencia) {
		this.agencia = agencia;
	}

}
