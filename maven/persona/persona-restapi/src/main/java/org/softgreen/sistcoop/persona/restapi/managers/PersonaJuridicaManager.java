package org.softgreen.sistcoop.persona.restapi.managers;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.softgreen.sistcoop.persona.clien.enums.TipoEmpresa;
import org.softgreen.sistcoop.persona.client.models.AccionistaModel;
import org.softgreen.sistcoop.persona.client.models.PersonaJuridicaModel;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalProvider;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.ejb.representations.idm.AccionistaRepresentation;
import org.softgreen.sistcoop.persona.ejb.representations.idm.PersonaJuridicaRepresentation;

@Stateless
public class PersonaJuridicaManager {
	
	@Inject
	protected TipoDocumentoProvider tipoDocumentoProvider;

	@Inject
	protected PersonaNaturalProvider personaNaturalProvider;

	public void updatePersonaJuridicaFromRep(PersonaJuridicaModel model, PersonaJuridicaRepresentation rep) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(rep.getTipoDocumento());

		model.setCodigoPais(rep.getCodigoPais());
		model.setTipoDocumento(tipoDocumentoModel);
		model.setNumeroDocumento(rep.getNumeroDocumento());

		model.setRazonSocial(rep.getRazonSocial());
		model.setNombreComercial(rep.getNombreComercial());
		model.setActividadPrincipal(rep.getActividadPrincipal());
		model.setFechaConstitucion(rep.getFechaConstitucion());
		model.setFinLucro(rep.isFinLucro());

		model.setUbigeo(rep.getUbigeo());
		model.setDireccion(rep.getDireccion());
		model.setReferencia(rep.getReferencia());
		model.setTelefono(rep.getTelefono());
		model.setCelular(rep.getCelular());
		model.setEmail(rep.getEmail());

		model.setTipoEmpresa(TipoEmpresa.lookup(rep.getTipoEmpresa()));
		model.commit();
	}

	public void updateAccionistaFromRep(AccionistaModel model, AccionistaRepresentation rep) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(rep.getTipoDocumento());

		PersonaNaturalModel personaNaturalModel = personaNaturalProvider.getPersonaNaturalByTipoNumeroDoc(tipoDocumentoModel, rep.getNumeroDocumento());
		model.setPersonaNatural(personaNaturalModel);
		model.setPorcentajeParticipacion(rep.getPorcentajeParticipacion());
		model.commit();
	}
}
