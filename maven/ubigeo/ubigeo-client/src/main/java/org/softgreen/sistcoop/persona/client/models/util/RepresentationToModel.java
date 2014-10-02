package org.softgreen.sistcoop.persona.client.models.util;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.softgreen.sistcoop.persona.clien.enums.EstadoCivil;
import org.softgreen.sistcoop.persona.clien.enums.Sexo;
import org.softgreen.sistcoop.persona.clien.enums.TipoEmpresa;
import org.softgreen.sistcoop.persona.clien.enums.TipoPersona;
import org.softgreen.sistcoop.persona.client.models.PersonaJuridicaModel;
import org.softgreen.sistcoop.persona.client.models.PersonaJuridicaProvider;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalProvider;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.client.representations.idm.PersonaJuridicaRepresentation;
import org.softgreen.sistcoop.persona.client.representations.idm.PersonaNaturalRepresentation;
import org.softgreen.sistcoop.persona.client.representations.idm.TipoDocumentoRepresentation;

@Stateless
public class RepresentationToModel {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static TipoDocumentoModel createTipoDocumento(TipoDocumentoRepresentation rep, TipoDocumentoProvider provider) {
		TipoDocumentoModel model = provider.addTipoDocumento(
				rep.getAbreviatura(), 
				rep.getDenominacion(), 
				rep.getCantidadCaracteres(), 
				TipoPersona.lookup(rep.getTipoPersona()));
				
		return model;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static PersonaNaturalModel createPersonaNatural(
			PersonaNaturalRepresentation rep, 
			TipoDocumentoModel tipoDocumentoModel, 
			PersonaNaturalProvider personaNaturalProvider) {		

		PersonaNaturalModel model = personaNaturalProvider.addPersonaNatural(
				rep.getCodigoPais(), 
				tipoDocumentoModel, 
				rep.getNumeroDocumento(), 
				rep.getApellidoPaterno(), 
				rep.getApellidoMaterno(), 
				rep.getNombres(), 
				rep.getFechaNacimiento(), 
				Sexo.lookup(rep.getSexo()));		

		model.setEstadoCivil(EstadoCivil.lookup(rep.getEstadoCivil()));
		model.setOcupacion(rep.getOcupacion());
		model.setUrlFoto(rep.getUrlFoto());
		model.setUrlFirma(rep.getUrlFirma());
		model.setUbigeo(rep.getUbigeo());
		model.setDireccion(rep.getDireccion());
		model.setReferencia(rep.getReferencia());
		model.setTelefono(rep.getTelefono());
		model.setCelular(rep.getCelular());
		model.setEmail(rep.getEmail());

		model.commit();
		return model;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static PersonaJuridicaModel createPersonaJuridica(
			PersonaJuridicaRepresentation rep, 			
			TipoDocumentoModel tipoDocumentoModel, 
			PersonaNaturalModel representanteLegal, 
			PersonaJuridicaProvider personaJuridicaProvider) {

		PersonaJuridicaModel model = personaJuridicaProvider.addPersonaJuridica(
				representanteLegal, 
				rep.getCodigoPais(), 
				tipoDocumentoModel, 
				rep.getNumeroDocumento(), 
				rep.getRazonSocial(), 
				rep.getFechaConstitucion(), 
				TipoEmpresa.lookup(rep.getTipoEmpresa()),
				rep.isFinLucro());
		
		model.setUbigeo(rep.getUbigeo());
		model.setDireccion(rep.getDireccion());
		model.setReferencia(rep.getReferencia());
		model.setTelefono(rep.getTelefono());
		model.setCelular(rep.getCelular());
		model.setEmail(rep.getEmail());

		return model;
	}

}
