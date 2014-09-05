package org.softgreen.organizacion.controller;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.interceptor.Interceptors;

import org.softgreen.exception.RollbackFailureException;
import org.softgreen.organizacion.entity.DetalleHistorial;
import org.softgreen.organizacion.entity.dto.GenericDetalle;
import org.softgreen.organizacion.entity.type.Tipotransaccioncompraventa;
import org.softgreen.organizacion.entity.type.TransaccionBovedaCajaOrigen;
import org.softgreen.organizacion.service.ts.SessionServiceTS;
import org.softgreen.organizacion.util.Guard;

@Stateless
@Named
@Interceptors(Guard.class)
@Remote(SessionServiceTS.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SessionServiceBeanTS implements SessionServiceTS {

	@Override
	public Long abrirCaja() throws RollbackFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long cerrarCaja(Map<String, DetalleHistorial> detalleCaja)
			throws RollbackFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long crearAporte(Long idSocio, BigDecimal monto, int mes, int anio,
			String referencia) throws RollbackFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long crearTransaccionBancaria(String numeroCuenta, BigDecimal monto,
			String referencia) throws RollbackFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long crearTransaccionCompraVenta(
			Tipotransaccioncompraventa tipoTransaccion, String monedaRecibida,
			String monedaEntregada, BigDecimal montoRecibido,
			BigDecimal montoEntregado, BigDecimal tasaCambio, String referencia)
			throws RollbackFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long crearTransferenciaBancaria(String numeroCuentaOrigen,
			String numeroCuentaDestino, BigDecimal monto, String referencia)
			throws RollbackFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void extornarTransaccion(Long idTransaccion)
			throws RollbackFailureException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long cancelarCuentaBancariaConRetiro(String numeroCuenta)
			throws RollbackFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long inactivarSocio(Long idSocio) throws RollbackFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long cancelarSocioConRetiro(Long idSocio)
			throws RollbackFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long crearPendiente(Integer idBoveda, BigDecimal monto,
			String observacion) throws RollbackFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long crearTransaccionBovedaCaja(Integer idBoveda,
			Set<GenericDetalle> detalleTransaccion,
			TransaccionBovedaCajaOrigen origen) throws RollbackFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long crearTransaccionCajaCaja(Integer idCajadestino,
			Integer idMoneda, BigDecimal monto, String observacion)
			throws RollbackFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancelarTransaccionBovedaCaja(Long idTransaccionBovedaCaja)
			throws RollbackFailureException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void confirmarTransaccionBovedaCaja(Long idTransaccionBovedaCaja)
			throws RollbackFailureException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelarTransaccionCajaCaja(Long idTransaccionCajaCaja)
			throws RollbackFailureException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void confirmarTransaccionCajaCaja(Long idTransaccionCajaCaja)
			throws RollbackFailureException {
		// TODO Auto-generated method stub
		
	}

	

}