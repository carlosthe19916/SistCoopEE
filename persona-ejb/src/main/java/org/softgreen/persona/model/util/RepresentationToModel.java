package org.softgreen.persona.model.util;

import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.softgreen.persona.model.PersonaJuridicaModel;
import org.softgreen.persona.model.PersonaNaturalModel;
import org.softgreen.persona.model.TipoDocumentoModel;
import org.softgreen.persona.model.type.EstadoCivil;
import org.softgreen.persona.model.type.Sexo;
import org.softgreen.persona.model.type.TipoPersona;
import org.softgreen.persona.provider.PersonaJuridicaProvider;
import org.softgreen.persona.provider.PersonaNaturalProvider;
import org.softgreen.persona.provider.TipoDocumentoProvider;
import org.softgreen.persona.representation.idm.PersonaJuridicaRepresentation;
import org.softgreen.persona.representation.idm.PersonaNaturalRepresentation;
import org.softgreen.persona.representation.idm.TipoDocumentoRepresentation;

@Stateless
public class RepresentationToModel {

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static TipoDocumentoModel createTipoDocumento(TipoDocumentoRepresentation rep, TipoDocumentoProvider provider) {
		String abreviatura = rep.getAbreviatura();
		String denominacion = rep.getDenominacion();
		int maxLength = rep.getCantidadCaracteres();
		TipoPersona tipoPersona = TipoPersona.valueOf(rep.getTipoPersona().toUpperCase());

		TipoDocumentoModel model = provider.addTipoDocumento(abreviatura, denominacion, maxLength, tipoPersona);
		return model;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static PersonaNaturalModel createPersonaNatural(PersonaNaturalRepresentation rep, PersonaNaturalProvider personaNaturalProvider, TipoDocumentoProvider tipoDocumentoProvider) {		
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(rep.getTipoDocumento());
		String numeroDocumento = rep.getNumeroDocumento();
		String paterno = rep.getApellidoPaterno();
		String materno = rep.getApellidoMaterno();
		String nombres = rep.getNombres();
		Sexo sexo = Sexo.lookup(rep.getSexo());
		Date fechaNacimiento = rep.getFechaNacimiento();
		
		EstadoCivil estadoCivil = EstadoCivil.lookup(rep.getEstadoCivil());
		String ocupacion = rep.getOcupacion();
		String urlFoto = rep.getUrlFoto();
		String urlFirma = rep.getUrlFirma();
		String ubigeo = rep.getUbigeo();
		String direccion = rep.getDireccion();
		String referencia = rep.getReferencia();
		String telefono = rep.getTelefono();
		String celular = rep.getCelular();
		String email = rep.getEmail();
		
		PersonaNaturalModel model = personaNaturalProvider.addPersonaNatural(tipoDocumentoModel, numeroDocumento, paterno, materno, nombres, sexo, fechaNacimiento);
		model.setEstadoCivil(estadoCivil);
		model.setOcupacion(ocupacion);
		model.setUrlFoto(urlFoto);
		model.setUrlFirma(urlFirma);
		model.setUbigeo(ubigeo);
		model.setDireccion(direccion);
		model.setReferencia(referencia);
		model.setTelefono(telefono);
		model.setCelular(celular);
		model.setEmail(email);
		
		model.commit();
		
		return model;
	}

	public static PersonaJuridicaModel createPersonaJuridica(PersonaJuridicaRepresentation rep, PersonaJuridicaProvider personaJuridicaProvider, TipoDocumentoProvider tipoDocumentoProvider) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(rep.getTipoDocumento());
		String numeroDocumento = rep.getNumeroDocumento();
		
		String razonSocial = rep.getRazonSocial();
		
		String ubigeo = rep.getUbigeo();
		String direccion = rep.getDireccion();
		String referencia = rep.getReferencia();
		String telefono = rep.getTelefono();
		String celular = rep.getCelular();
		String email = rep.getEmail();
		
		PersonaJuridicaModel model = personaJuridicaProvider.addPersonaJuridica(tipoDocumentoModel, numeroDocumento, razonSocial);
		
		
		model.setUbigeo(ubigeo);
		model.setDireccion(direccion);
		model.setReferencia(referencia);
		model.setTelefono(telefono);
		model.setCelular(celular);
		model.setEmail(email);
		
		model.commit();
		
		return model;
	}

}
