package org.softgreen.organizacion.controller;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.softgreen.dao.DAO;
import org.softgreen.dao.QueryParameter;
import org.softgreen.organizacion.entity.Boveda;
import org.softgreen.organizacion.service.nt.BovedaServiceNT;

@Named
@Stateless
@Remote(BovedaServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BovedaServiceBeanNT implements BovedaServiceNT {

	@Inject
	private DAO<Integer, Boveda> bovedaDAO;

	@Override
	public Boveda findById(Integer id) {
		return bovedaDAO.find(id);
	}

	@Override
	public List<Boveda> findAll() {
		return bovedaDAO.findAll();
	}

	@Override
	public int count() {
		return bovedaDAO.count();
	}

	@Override
	public List<Boveda> findAll(Integer idAgencia) {
		if (idAgencia == null)
			return null;
		QueryParameter queryParameter = QueryParameter.with("idAgencia", idAgencia);
		List<Boveda> list = bovedaDAO.findByNamedQuery(Boveda.findAllByIdAgencia, queryParameter.parameters());		
		return list;
	}

}
