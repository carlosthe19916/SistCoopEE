package org.softgreen.organizacion.controller;

import java.math.BigInteger;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;

import org.softgreen.organizacion.entity.dto.VoucherTransaccionBovedaCaja;
import org.softgreen.organizacion.entity.dto.VoucherTransaccionCajaCaja;
import org.softgreen.organizacion.service.nt.TransaccionInternaServiceNT;

@Named
@Stateless
@Remote(TransaccionInternaServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TransaccionInternaServiceBeanNT implements TransaccionInternaServiceNT {

	@Override
	public VoucherTransaccionBovedaCaja getVoucherTransaccionBovedaCaja(
			BigInteger idTransaccionBovedaCaja) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VoucherTransaccionCajaCaja getVoucherTransaccionCajaCaja(
			BigInteger idTransaccionCajaCaja) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
