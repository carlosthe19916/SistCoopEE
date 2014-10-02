package org.softgreen.sistcoop.persona.client.models.util;

import java.util.HashSet;
import java.util.Set;

import org.softgreen.sistcoop.persona.client.models.AccionistaModel;
import org.softgreen.sistcoop.persona.client.models.PersonaJuridicaModel;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.client.representations.idm.AccionistaRepresentation;
import org.softgreen.sistcoop.persona.client.representations.idm.PersonaJuridicaRepresentation;
import org.softgreen.sistcoop.persona.client.representations.idm.PersonaNaturalRepresentation;
import org.softgreen.sistcoop.persona.client.representations.idm.TipoDocumentoRepresentation;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class ModelToRepresentation {

	public static TipoDocumentoRepresentation toRepresentation(TipoDocumentoModel model) {
		if (model == null)
			return null;
		TipoDocumentoRepresentation rep = new TipoDocumentoRepresentation();

		rep.setAbreviatura(model.getAbreviatura());
		rep.setDenominacion(model.getDenominacion());
		rep.setCantidadCaracteres(model.getCantidadCaracteres());
		rep.setTipoPersona(model.getTipoPersona().toString());

		return rep;
	}

	public static PersonaNaturalRepresentation toRepresentation(PersonaNaturalModel model) {
		if (model == null)
			return null;

		PersonaNaturalRepresentation rep = new PersonaNaturalRepresentation();

		rep.setId(model.getId());

		rep.setCodigoPais(model.getCodigoPais());
		rep.setTipoDocumento(model.getTipoDocumento().getAbreviatura());
		rep.setNumeroDocumento(model.getNumeroDocumento());

		// datos personales
		rep.setApellidoPaterno(model.getApellidoPaterno());
		rep.setApellidoMaterno(model.getApellidoMaterno());
		rep.setNombres(model.getNombres());
		rep.setFechaNacimiento(model.getFechaNacimiento());
		rep.setSexo(model.getSexo().toString());

		rep.setEstadoCivil(model.getEstadoCivil().toString());
		rep.setOcupacion(model.getOcupacion());

		// direccion de residencia
		rep.setUbigeo(model.getUbigeo());
		rep.setDireccion(model.getDireccion());
		rep.setReferencia(model.getReferencia());
		rep.setTelefono(model.getTelefono());
		rep.setCelular(model.getCelular());
		rep.setEmail(model.getEmail());

		rep.setUrlFoto(model.getUrlFoto());
		rep.setUrlFirma(model.getUrlFirma());

		return rep;
	}

	public static PersonaJuridicaRepresentation toRepresentation(PersonaJuridicaModel model) {
		if (model == null)
			return null;

		PersonaJuridicaRepresentation rep = new PersonaJuridicaRepresentation();

		rep.setId(model.getId());

		rep.setCodigoPais(model.getCodigoPais());
		rep.setTipoDocumento(model.getTipoDocumento().getAbreviatura());
		rep.setNumeroDocumento(model.getNumeroDocumento());

		// datos personales
		rep.setRazonSocial(model.getRazonSocial());
		rep.setActividadPrincipal(model.getActividadPrincipal());
		rep.setFechaConstitucion(model.getFechaConstitucion());
		rep.setFinLucro(model.isFinLucro());
		rep.setNombreComercial(model.getNombreComercial());
		rep.setTipoEmpresa(model.getTipoEmpresa().toString());

		// representante legal
		rep.setIdRepresentanteLegal(model.getRepresentanteLegal().getId());
		rep.setTipoDocumentoRepresentanteLegal(model.getRepresentanteLegal().getTipoDocumento().getAbreviatura());
		rep.setNumeroDocumentoRepresentanteLegal(model.getRepresentanteLegal().getNumeroDocumento());

		// direccion de residencia
		rep.setUbigeo(model.getUbigeo());
		rep.setDireccion(model.getDireccion());
		rep.setReferencia(model.getReferencia());
		rep.setTelefono(model.getTelefono());
		rep.setCelular(model.getCelular());
		rep.setEmail(model.getEmail());

		// accionistas
		Set<Long> accionistas = new HashSet<Long>();
		for (AccionistaModel accionistaModel : model.getAccionistas()) {
			accionistas.add(accionistaModel.getId());
		}
		rep.setAccionistas(accionistas);

		return rep;
	}

	public static AccionistaRepresentation toRepresentation(AccionistaModel model) {
		if (model == null)
			return null;

		AccionistaRepresentation rep = new AccionistaRepresentation();

		rep.setId(model.getId());
		rep.setPorcentajeParticipacion(model.getPorcentajeParticipacion());

		rep.setIdPersonaNatural(model.getPersonaNatural().getId());
		rep.setCodigoPais(model.getPersonaNatural().getCodigoPais());
		rep.setTipoDocumento(model.getPersonaNatural().getTipoDocumento().getAbreviatura());
		rep.setNumeroDocumento(model.getPersonaNatural().getNumeroDocumento());

		rep.setApellidoPaterno(model.getPersonaNatural().getApellidoPaterno());
		rep.setApellidoMaterno(model.getPersonaNatural().getApellidoMaterno());
		rep.setNombres(model.getPersonaNatural().getNombres());
		rep.setFechaNacimiento(model.getPersonaNatural().getFechaNacimiento());
		rep.setSexo(model.getPersonaNatural().getSexo().toString());

		return rep;
	}

}
