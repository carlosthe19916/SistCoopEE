package org.softgreen.sistcoop.organizacion.client.models.util;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.softgreen.sistcoop.organizacion.clien.enums.EstadoCivil;
import org.softgreen.sistcoop.organizacion.clien.enums.Sexo;
import org.softgreen.sistcoop.organizacion.clien.enums.TipoEmpresa;
import org.softgreen.sistcoop.organizacion.clien.enums.TipoPersona;
import org.softgreen.sistcoop.organizacion.client.models.PersonaJuridicaModel;
import org.softgreen.sistcoop.organizacion.client.models.PersonaJuridicaProvider;
import org.softgreen.sistcoop.organizacion.client.models.PersonaNaturalModel;
import org.softgreen.sistcoop.organizacion.client.models.PersonaNaturalProvider;
import org.softgreen.sistcoop.organizacion.client.models.SucursalModel;
import org.softgreen.sistcoop.organizacion.client.models.SucursalProvider;
import org.softgreen.sistcoop.organizacion.client.representations.idm.PersonaJuridicaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.PersonaNaturalRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.SucursalRepresentation;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RepresentationToModel {
		
	public SucursalModel createTipoDocumento(SucursalRepresentation rep, SucursalProvider provider) {
		SucursalModel model = provider.addTipoDocumento(
				rep.getAbreviatura(), 
				rep.getDenominacion(), 
				rep.getCantidadCaracteres(), 
				TipoPersona.valueOf(rep.getTipoPersona()));	
		return model;
	}
		
	public PersonaNaturalModel createPersonaNatural(
			PersonaNaturalRepresentation rep, 
			SucursalModel tipoDocumentoModel, 
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

		model.setEstadoCivil(rep.getEstadoCivil() != null ? EstadoCivil.valueOf(rep.getEstadoCivil().toUpperCase()) : null);		
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
			SucursalModel tipoDocumentoModel, 
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
		
		model.setActividadPrincipal(rep.getActividadPrincipal());
		model.setNombreComercial(rep.getNombreComercial());
		
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
