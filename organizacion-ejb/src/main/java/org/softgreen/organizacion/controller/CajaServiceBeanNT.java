package org.softgreen.organizacion.controller;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;

import org.softgreen.organizacion.entity.Boveda;
import org.softgreen.organizacion.entity.Caja;
import org.softgreen.organizacion.entity.DetalleHistorial;
import org.softgreen.organizacion.entity.HistorialCaja;
import org.softgreen.organizacion.entity.PendienteCaja;
import org.softgreen.organizacion.entity.TransaccionBovedaCaja;
import org.softgreen.organizacion.entity.TransaccionCajaCaja;
import org.softgreen.organizacion.entity.dto.ResumenOperacionesCaja;
import org.softgreen.organizacion.entity.dto.VoucherCompraVenta;
import org.softgreen.organizacion.entity.dto.VoucherTransaccionBancaria;
import org.softgreen.organizacion.entity.dto.VoucherTransaccionCuentaAporte;
import org.softgreen.organizacion.entity.dto.VoucherTransferenciaBancaria;
import org.softgreen.organizacion.service.nt.CajaServiceNT;

@Named
@Stateless
@Remote(CajaServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CajaServiceBeanNT implements CajaServiceNT {

	@Override
	public Caja findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Caja> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<Boveda> getBovedas(Integer idCaja) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<HistorialCaja> getHistorialCaja(Integer idCaja, Date dateDesde,
			Date dateHasta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<DetalleHistorial> getDetalleCaja(Integer idCaja,
			Long idHistorialCaja) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransaccionBovedaCaja> getTransaccionesEnviadasBovedaCaja(
			Integer idCaja, Long idHistorial) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransaccionBovedaCaja> getTransaccionesRecibidasBovedaCaja(
			Integer idCaja, Long idHistorial) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<TransaccionCajaCaja> getTransaccionesEnviadasCajaCaja(
			BigInteger idCaja, BigInteger idHistorialCaja) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<TransaccionCajaCaja> getTransaccionesRecibidasCajaCaja(
			BigInteger idCaja, BigInteger idHistorialCaja) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResumenOperacionesCaja getResumenOperacionesCaja(
			BigInteger idHistorial) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VoucherTransaccionCuentaAporte getVoucherCuentaAporte(
			BigInteger idTransaccion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VoucherTransaccionBancaria getVoucherTransaccionBancaria(
			BigInteger idTransaccionBancaria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VoucherTransferenciaBancaria getVoucherTransferenciaBancaria(
			BigInteger idTransferencia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VoucherCompraVenta getVoucherCompraVenta(
			BigInteger idTransaccionCompraVenta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PendienteCaja> getPendientes(BigInteger idCaja,
			BigInteger idHistorialCaja) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
