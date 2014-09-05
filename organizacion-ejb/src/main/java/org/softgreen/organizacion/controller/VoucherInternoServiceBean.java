package org.softgreen.organizacion.controller;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;

import org.softgreen.organizacion.entity.voucher.VoucherCierreCaja;
import org.softgreen.organizacion.entity.voucher.VoucherCompraVenta;
import org.softgreen.organizacion.entity.voucher.VoucherResumenOperacionesCaja;
import org.softgreen.organizacion.entity.voucher.VoucherTransaccionBancaria;
import org.softgreen.organizacion.entity.voucher.VoucherTransaccionCuentaAporte;
import org.softgreen.organizacion.entity.voucher.VoucherTransferenciaBancaria;
import org.softgreen.organizacion.service.VoucherInternoService;

@Named
@Stateless
@Remote(VoucherInternoService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VoucherInternoServiceBean implements VoucherInternoService {

	@Override
	public VoucherCierreCaja getVoucherCierreCaja(Long idHistorialCaja) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VoucherResumenOperacionesCaja getResumenOperacionesCaja(Long idHistorial) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VoucherTransaccionCuentaAporte getVoucherCuentaAporte(Long idTransaccion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VoucherTransaccionBancaria getVoucherTransaccionBancaria(Long idTransaccion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VoucherTransferenciaBancaria getVoucherTransferenciaBancaria(Long idTransaccion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VoucherCompraVenta getVoucherCompraVenta(Long idTransaccion) {
		// TODO Auto-generated method stub
		return null;
	}

}
