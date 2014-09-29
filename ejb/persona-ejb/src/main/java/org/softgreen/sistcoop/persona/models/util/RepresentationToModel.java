package org.softgreen.sistcoop.persona.models.util;

import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jboss.logging.Logger;
import org.softgreen.sistcoop.persona.enums.EstadoCivil;
import org.softgreen.sistcoop.persona.enums.Sexo;
import org.softgreen.sistcoop.persona.enums.TipoEmpresa;
import org.softgreen.sistcoop.persona.models.PersonaJuridicaModel;
import org.softgreen.sistcoop.persona.models.PersonaJuridicaProvider;
import org.softgreen.sistcoop.persona.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.models.PersonaNaturalProvider;
import org.softgreen.sistcoop.persona.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.representations.idm.PersonaJuridicaRepresentation;
import org.softgreen.sistcoop.persona.representations.idm.PersonaNaturalRepresentation;
import org.softgreen.sistcoop.persona.representations.idm.TipoDocumentoRepresentation;

@Stateless
public class RepresentationToModel {

	private static Logger logger = Logger.getLogger(RepresentationToModel.class);

	public static TipoDocumentoModel createTipoDocumento(TipoDocumentoRepresentation rep, TipoDocumentoProvider provider) {
		/*TipoDocumentoEntity tipoDocumentoEntity = new TipoDocumentoEntity();
		tipoDocumentoEntity.setAbreviatura(rep.getAbreviatura());
		tipoDocumentoEntity.setDenominacion(rep.getDenominacion());
		tipoDocumentoEntity.setCantidadCaracteres(rep.getCantidadCaracteres());
		tipoDocumentoEntity.setTipoPersona(TipoPersona.lookup(rep.getTipoPersona()));
		TipoDocumentoModel model = provider.addTipoDocumento(tipoDocumentoEntity);
		return model;*/
		return null;
	}

	public static PersonaNaturalModel createPersonaNatural(PersonaNaturalRepresentation rep, TipoDocumentoModel tipoDocumentoModel, PersonaNaturalProvider personaNaturalProvider) {
		String codigoPais = rep.getCodigoPais();
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

		PersonaNaturalModel model = personaNaturalProvider.getPersonaNatural();

		model.setCodigoPais(codigoPais);
		model.setTipoDocumento(tipoDocumentoModel);
		model.setNumeroDocumento(numeroDocumento);
		model.setApellidoPaterno(paterno);
		model.setApellidoMaterno(materno);
		model.setNombres(nombres);
		model.setSexo(sexo);
		model.setFechaNacimiento(fechaNacimiento);

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

		personaNaturalProvider.updatePersonaNatural(model);
		return model;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static PersonaJuridicaModel createPersonaJuridica(
			PersonaJuridicaRepresentation rep, 
			PersonaNaturalModel representanteLegal, 
			TipoDocumentoModel tipoDocumentoModel, 
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
