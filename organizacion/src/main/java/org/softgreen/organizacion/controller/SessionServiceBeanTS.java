package org.softgreen.organizacion.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.interceptor.Interceptors;

import org.softgreen.dao.QueryParameter;
import org.softgreen.exception.RollbackFailureException;
import org.softgreen.organizacion.entity.Caja;
import org.softgreen.organizacion.entity.DetalleHistorial;
import org.softgreen.organizacion.entity.HistorialCaja;
import org.softgreen.organizacion.entity.PersonaNatural;
import org.softgreen.organizacion.entity.Trabajador;
import org.softgreen.organizacion.entity.dto.GenericDetalle;
import org.softgreen.organizacion.entity.type.Tipotransaccionbancaria;
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

	private HistorialCaja getHistorialActivo() {
		return null;
	}
	
	private Caja getCaja() {
		return null;
	}
	
	private Trabajador getTrabajador() {
		return null;
	}
	
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
		
		Socio socio = socioDAO.find(idSocio);
		if (socio == null)
			throw new RollbackFailureException("Socio no encontrado");
		CuentaAporte cuentaAporte = socio.getCuentaAporte();
		if (cuentaAporte == null)
			throw new RollbackFailureException("Socio no tiene cuenta de aportes");

		if (monto.compareTo(BigDecimal.ZERO) != 1) {
			throw new RollbackFailureException("Monto invalido para transaccion");
		}

		switch (cuentaAporte.getEstadoCuenta()) {
		case CONGELADO:
			throw new RollbackFailureException(
					"Cuenta CONGELADA, no se pueden realizar transacciones");
		case INACTIVO:
			throw new RollbackFailureException(
					"Cuenta INACTIVO, no se pueden realizar transacciones");
		default:
			break;
		}

		// obteniendo datos de caja en session
		HistorialCaja historialCaja = this.getHistorialActivo();
		Trabajador trabajador = this.getTrabajador();
		PersonaNatural natural = trabajador.getPersonaNatural();

		// obteniendo saldo disponible de cuenta
		BigDecimal saldoDisponible = cuentaAporte.getSaldo().add(monto);
		cuentaAporte.setSaldo(saldoDisponible);
		cuentaAporteDAO.update(cuentaAporte);

		Calendar calendar = Calendar.getInstance();

		TransaccionCuentaAporte transaccionCuentaAporte = new TransaccionCuentaAporte();
		transaccionCuentaAporte.setAnioAfecta(anio);
		transaccionCuentaAporte.setMesAfecta(mes);
		transaccionCuentaAporte.setCuentaAporte(cuentaAporte);
		transaccionCuentaAporte.setEstado(true);
		transaccionCuentaAporte.setFecha(calendar.getTime());
		transaccionCuentaAporte.setHistorialCaja(historialCaja);
		transaccionCuentaAporte.setHora(calendar.getTime());
		transaccionCuentaAporte.setMonto(monto);
		transaccionCuentaAporte.setNumeroOperacion(this.getNumeroOperacion());
		transaccionCuentaAporte.setReferencia(referencia);
		transaccionCuentaAporte.setObservacion("Doc:"
				+ natural.getTipoDocumento().getAbreviatura() + "/"
				+ natural.getNumeroDocumento() + "Trabajador:"
				+ natural.getApellidoPaterno() + " "
				+ natural.getApellidoMaterno() + "," + natural.getNombres());
		transaccionCuentaAporte.setSaldoDisponible(saldoDisponible);
		transaccionCuentaAporte
				.setTipoTransaccion(Tipotransaccionbancaria.DEPOSITO);

		transaccionCuentaAporteDAO.create(transaccionCuentaAporte);
		// actualizar saldo caja
		this.actualizarSaldoCaja(monto, cuentaAporte.getMoneda().getIdMoneda());
		return transaccionCuentaAporte.getIdTransaccionCuentaAporte();
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