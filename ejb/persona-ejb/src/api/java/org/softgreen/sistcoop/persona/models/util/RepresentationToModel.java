package org.softgreen.sistcoop.persona.models.util;

import java.util.Date;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jboss.logging.Logger;
import org.softgreen.sistcoop.persona.enums.EstadoCivil;
import org.softgreen.sistcoop.persona.enums.Sexo;
import org.softgreen.sistcoop.persona.enums.TipoPersona;
import org.softgreen.sistcoop.persona.models.PersonaJuridicaModel;
import org.softgreen.sistcoop.persona.models.PersonaJuridicaProvider;
import org.softgreen.sistcoop.persona.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.models.PersonaNaturalProvider;
import org.softgreen.sistcoop.persona.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.representations.idm.PersonaJuridicaRepresentation;
import org.softgreen.sistcoop.persona.representations.idm.PersonaNaturalRepresentation;
import org.softgreen.sistcoop.persona.representations.idm.TipoDocumentoRepresentation;

public class RepresentationToModel {

    private static Logger logger = Logger.getLogger(RepresentationToModel.class);

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
		
		return model;
	}

}
