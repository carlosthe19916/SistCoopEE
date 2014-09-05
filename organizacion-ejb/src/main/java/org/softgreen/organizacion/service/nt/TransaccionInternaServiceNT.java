package org.softgreen.organizacion.service.nt;

import java.math.BigInteger;

import javax.ejb.Remote;

import org.softgreen.organizacion.entity.dto.VoucherTransaccionBovedaCaja;
import org.softgreen.organizacion.entity.dto.VoucherTransaccionCajaCaja;


@Remote
public interface TransaccionInternaServiceNT {
	
	public VoucherTransaccionBovedaCaja getVoucherTransaccionBovedaCaja(BigInteger idTransaccionBovedaCaja);
	
	public VoucherTransaccionCajaCaja getVoucherTransaccionCajaCaja(BigInteger idTransaccionCajaCaja);
	
}
