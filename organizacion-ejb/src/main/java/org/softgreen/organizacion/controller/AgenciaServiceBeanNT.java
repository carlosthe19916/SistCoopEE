package org.softgreen.organizacion.controller;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.softgreen.dao.DAO;
import org.softgreen.dao.QueryParameter;
import org.softgreen.organizacion.entity.Agencia;
import org.softgreen.organizacion.entity.Caja;
import org.softgreen.organizacion.service.nt.AgenciaServiceNT;

@Named
@Stateless
@Remote(AgenciaServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AgenciaServiceBeanNT implements AgenciaServiceNT {

	@Inject
	private DAO<Integer, Agencia> agenciaDAO;

	@Override
	public Agencia findById(Integer id) {		
		return agenciaDAO.find(id);
	}

	@Override
	public List<Agencia> findAll() {
		return agenciaDAO.findAll();
	}

	@Override
	public int count() {
		return agenciaDAO.count();
	}

	@Override
	public Set<Caja> getCajasOfAgencia(Integer idAgencia) {
		Agencia agencia = agenciaDAO.find(idAgencia);
		if (agencia != null) {
			return agencia.getCajas();
		} else {
			return null;
		}
	}

	@Override
	public Agencia findByCodigo(String codigo) {
		QueryParameter queryParameter = QueryParameter.with("codigo", codigo);
		List<Agencia> list = agenciaDAO.findByNamedQuery(Agencia.findByCodigo,
				queryParameter.parameters());
		if (list.size() <= 1) {
			Agencia agencia = null;
			for (Agencia a : list) {
				agencia = a;
			}
			return agencia;
		} else {
			return null;
		}
	}

}
