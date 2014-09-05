package org.softgreen.organizacion.controller;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;

import org.softgreen.exception.NonexistentEntityException;
import org.softgreen.organizacion.service.nt.TrabajadorServiceNT;

@Named
@Stateless
@Remote(TrabajadorServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TrabajadorServiceBeanNT implements TrabajadorServiceNT {

	@Override
	public org.softgreen.organizacion.entity.Trabajador findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<org.softgreen.organizacion.entity.Trabajador> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public org.softgreen.organizacion.entity.Trabajador findByUsuario(
			String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public org.softgreen.organizacion.entity.Caja findByTrabajador(
			BigInteger idTrabajador) throws NonexistentEntityException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public org.softgreen.organizacion.entity.Agencia getAgencia(
			BigInteger idTrabajador) throws NonexistentEntityException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
