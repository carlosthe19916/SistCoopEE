package org.softgreen.ubigeo.controller;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.softgreen.ubigeo.dao.DAO;
import org.softgreen.ubigeo.dao.QueryParameter;
import org.softgreen.ubigeo.entity.Departamento;
import org.softgreen.ubigeo.entity.Distrito;
import org.softgreen.ubigeo.entity.Provincia;
import org.softgreen.ubigeo.service.UbigeoService;

@Named
@Stateless
@Remote(UbigeoService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UbigeoServiceBean implements UbigeoService {

	@Inject
	private DAO<String, Departamento> departamentoDAO;

	@Inject
	private DAO<String, Provincia> provinciaDAO;

	@Inject
	private DAO<String, Distrito> distritoDAO;

	@Override
	public List<Departamento> getDepartamentos() {
		return departamentoDAO.findAll();
	}

	@Override
	public List<Provincia> getProvincias(String codigoDepartamento) {
		QueryParameter queryParameter = QueryParameter.with("codigoDepartamento", codigoDepartamento);
		List<Provincia> list = provinciaDAO.findByNamedQuery(Provincia.findByCodDepartamento, queryParameter.parameters());
		return list;
	}

	@Override
	public List<Distrito> getDistritos(String codigoDepartamento, String codigoProvincia) {
		QueryParameter queryParameter = QueryParameter.with("codigoDepartamento", codigoDepartamento).and("codigoProvincia", codigoProvincia);
		List<Distrito> list = distritoDAO.findByNamedQuery(Distrito.findByCodDepartamentoProvincia, queryParameter.parameters());
		return list;
	}

}
