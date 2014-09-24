package org.softgreen.organizacion.service;

import javax.ejb.Remote;

import org.softgreen.organizacion.entity.voucher.VoucherCierreCaja;
import org.softgreen.organizacion.entity.voucher.VoucherCompraVenta;
import org.softgreen.organizacion.entity.voucher.VoucherResumenOperacionesCaja;
import org.softgreen.organizacion.entity.voucher.VoucherTransaccionBancaria;
import org.softgreen.organizacion.entity.voucher.VoucherTransaccionCuentaAporte;
import org.softgreen.organizacion.entity.voucher.VoucherTransferenciaBancaria;

@Remote
public interface VoucherInternoService {

	public VoucherCierreCaja getVoucherCierreCaja(Long idHistorialCaja);

	public VoucherResumenOperacionesCaja getResumenOperacionesCaja(Long idHistorial);

	public VoucherTransaccionCuentaAporte getVoucherCuentaAporte(Long idTransaccion);

	public VoucherTransaccionBancaria getVoucherTransaccionBancaria(Long idTransaccion);

	public VoucherTransferenciaBancaria getVoucherTransferenciaBancaria(Long idTransaccion);

	public VoucherCompraVenta getVoucherCompraVenta(Long idTransaccion);

}
