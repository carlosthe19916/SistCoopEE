package org.softgreen.organizacion.controller;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;

import org.softgreen.organizacion.service.nt.SessionServiceNT;

@Stateless
@Named
@Remote(SessionServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SessionServiceBeanNT implements SessionServiceNT {

	@Override
	public org.softgreen.organizacion.entity.Caja getCajaOfSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public org.softgreen.organizacion.entity.Agencia getAgenciaOfSession() {
		// TODO Auto-generated method stub
		return null;
	}

	

}