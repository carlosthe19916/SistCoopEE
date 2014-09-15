package org.softgreen.persona.controller;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.softgreen.persona.dao.DAO;
import org.softgreen.persona.dao.QueryParameter;
import org.softgreen.persona.entity.TipoDocumento;
import org.softgreen.persona.entity.type.TipoPersona;
import org.softgreen.persona.service.MaestroService;

@Named
@Stateless
@Remote(MaestroService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MaestroServiceBean implements MaestroService {

	@Inject
	private DAO<String, TipoDocumento> tipoDocumentoDAO;

	@Override
	public List<TipoDocumento> getTipoDocumento(TipoPersona tipoPersona) {
		if(tipoPersona != null){
			List<TipoDocumento> list = null;
			QueryParameter queryParameter = QueryParameter.with("tipoPersona", tipoPersona);
			list = tipoDocumentoDAO.findByNamedQuery(TipoDocumento.findByTipopersona, queryParameter.parameters());
			return list;
		} else {
			return tipoDocumentoDAO.findAll();
		}
		
	}
}
