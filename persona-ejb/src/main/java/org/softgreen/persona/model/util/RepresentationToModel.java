package org.softgreen.persona.model.util;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.softgreen.persona.model.PersonaJuridicaModel;
import org.softgreen.persona.model.PersonaNaturalModel;
import org.softgreen.persona.model.TipoDocumentoModel;
import org.softgreen.persona.model.type.TipoPersona;
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

	public static PersonaNaturalModel createPersonaNatural(PersonaNaturalRepresentation personaNaturalRepresentation) {
		return null;
	}

	public static PersonaJuridicaModel createPersonaJuridica(PersonaJuridicaRepresentation personaJuridicaRepresentation) {
		return null;
	}

}
