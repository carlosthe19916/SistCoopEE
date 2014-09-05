package org.softgreen.organizacion.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Hibernate;
import org.softgreen.dao.DAO;
import org.softgreen.organizacion.entity.Agencia;
import org.softgreen.organizacion.entity.BovedaCaja;
import org.softgreen.organizacion.entity.Caja;
import org.softgreen.organizacion.entity.PendienteCaja;
import org.softgreen.organizacion.entity.dto.VoucherPendienteCaja;
import org.softgreen.organizacion.service.nt.PendienteServiceNT;

@Named
@Stateless
@Remote(PendienteServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PendienteServiceBeanNT implements PendienteServiceNT {

	@Override
	public PendienteCaja findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PendienteCaja> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public VoucherPendienteCaja getVoucherPendienteCaja(Long idPendienteCaja) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
