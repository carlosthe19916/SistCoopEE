package org.softgreen.organizacion.service.ts;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import javax.ejb.Remote;

import org.softgreen.exception.RollbackFailureException;
import org.softgreen.organizacion.entity.DetalleHistorial;
import org.softgreen.organizacion.entity.dto.GenericDetalle;
import org.softgreen.organizacion.entity.type.Tipotransaccioncompraventa;
import org.softgreen.organizacion.entity.type.TransaccionBovedaCajaOrigen;

@Remote
public interface SessionServiceTS {

	public Long abrirCaja() throws RollbackFailureException;

	public Long cerrarCaja(Map<String, DetalleHistorial> detalleCaja) throws RollbackFailureException;

	public Long crearAporte(Long idSocio, BigDecimal monto, int mes, int anio, String referencia) throws RollbackFailureException;

	public Long crearTransaccionBancaria(String numeroCuenta, BigDecimal monto, String referencia) throws RollbackFailureException;

	public Long crearTransaccionCompraVenta(Tipotransaccioncompraventa tipoTransaccion, String monedaRecibida, String monedaEntregada, BigDecimal montoRecibido, BigDecimal montoEntregado, BigDecimal tasaCambio, String referencia) throws RollbackFailureException;

	public Long crearTransferenciaBancaria(String numeroCuentaOrigen, String numeroCuentaDestino, BigDecimal monto, String referencia) throws RollbackFailureException;

	public void extornarTransaccion(Long idTransaccion) throws RollbackFailureException;
	
	public Long cancelarCuentaBancariaConRetiro(String numeroCuenta) throws RollbackFailureException;

	public Long inactivarSocio(Long idSocio) throws RollbackFailureException;
	
	public Long cancelarSocioConRetiro(Long idSocio) throws RollbackFailureException;

	public Long crearPendiente(Integer idBoveda, BigDecimal monto, String observacion) throws RollbackFailureException;

	public Long crearTransaccionBovedaCaja(Integer idBoveda, Set<GenericDetalle> detalleTransaccion, TransaccionBovedaCajaOrigen origen) throws RollbackFailureException;

	public Long crearTransaccionCajaCaja(Integer idCajadestino, Integer idMoneda, BigDecimal monto, String observacion) throws RollbackFailureException;

	public void cancelarTransaccionBovedaCaja(Long idTransaccionBovedaCaja) throws RollbackFailureException;

	public void confirmarTransaccionBovedaCaja(Long idTransaccionBovedaCaja) throws RollbackFailureException;

	public void cancelarTransaccionCajaCaja(Long idTransaccionCajaCaja) throws RollbackFailureException;

	public void confirmarTransaccionCajaCaja(Long idTransaccionCajaCaja) throws RollbackFailureException;

}
