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
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RepresentationToModel {
		
	public TipoDocumentoModel createTipoDocumento(TipoDocumentoRepresentation rep, TipoDocumentoProvider provider) {
		TipoDocumentoModel model = provider.addTipoDocumento(
				rep.getAbreviatura(), 
				rep.getDenominacion(), 
				rep.getCantidadCaracteres(), 
				TipoPersona.valueOf(rep.getTipoPersona()));	
		return model;
	}
		
	public PersonaNaturalModel createPersonaNatural(
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
				Sexo.valueOf(rep.getSexo().toUpperCase()));		

		model.setEstadoCivil(EstadoCivil.valueOf(rep.getEstadoCivil().toUpperCase()));
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
	
	public PersonaJuridicaModel createPersonaJuridica(
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
				TipoEmpresa.valueOf(rep.getTipoEmpresa().toUpperCase()),
				rep.isFinLucro());
		
		model.setUbigeo(rep.getUbigeo());
		model.setDireccion(rep.getDireccion());
		model.setReferencia(rep.getReferencia());
		model.setTelefono(rep.getTelefono());
		model.setCelular(rep.getCelular());
		model.setEmail(rep.getEmail());

		model.commit();		
		return model;
	}

}
