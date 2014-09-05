package org.softgreen.organizacion.controller;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;

import org.softgreen.exception.NonexistentEntityException;
import org.softgreen.exception.PreexistingEntityException;
import org.softgreen.exception.RollbackFailureException;
import org.softgreen.organizacion.service.ts.CajaServiceTS;

@Named
@Stateless
@Remote(CajaServiceTS.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CajaServiceBeanTS implements CajaServiceTS {

	@Override
	public Integer create(org.softgreen.organizacion.entity.Caja t)
			throws PreexistingEntityException, RollbackFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, org.softgreen.organizacion.entity.Caja t)
			throws NonexistentEntityException, PreexistingEntityException,
			RollbackFailureException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) throws NonexistentEntityException,
			RollbackFailureException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void desactivar(Integer idCaja) throws RollbackFailureException {
		// TODO Auto-generated method stub
		
	}

	

}
