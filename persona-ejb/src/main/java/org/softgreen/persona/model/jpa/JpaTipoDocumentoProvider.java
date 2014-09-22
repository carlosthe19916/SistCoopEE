package org.softgreen.persona.model.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.softgreen.persona.model.PersonaJuridicaModel;
import org.softgreen.persona.model.PersonaNaturalModel;
import org.softgreen.persona.model.TipoDocumentoModel;
import org.softgreen.persona.model.jpa.entity.PersonaNaturalEntity;
import org.softgreen.persona.provider.PersonaJuridicaProvider;
import org.softgreen.persona.provider.PersonaNaturalProvider;
import org.softgreen.persona.provider.TipoDocumentoProvider;

public class JpaTipoDocumentoProvider implements TipoDocumentoProvider {

	protected EntityManager em;

	public JpaTipoDocumentoProvider(EntityManager em) {
		this.em = em;
	}

	@Override
	public TipoDocumentoModel getTipoDocumentoByAbreviatura(String abreviatura) {
		// TODO Auto-generated method stub
		return null;
	}

}
