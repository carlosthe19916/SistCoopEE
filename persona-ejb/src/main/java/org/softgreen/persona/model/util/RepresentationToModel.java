package org.softgreen.persona.model.util;

import org.softgreen.persona.model.PersonaJuridicaModel;
import org.softgreen.persona.model.PersonaNaturalModel;
import org.softgreen.persona.model.TipoDocumentoModel;
import org.softgreen.persona.model.jpa.TipoDocumentoAdapter;
import org.softgreen.persona.model.type.TipoPersona;
import org.softgreen.persona.provider.TipoDocumentoProvider;
import org.softgreen.persona.representation.idm.PersonaJuridicaRepresentation;
import org.softgreen.persona.representation.idm.PersonaNaturalRepresentation;
import org.softgreen.persona.representation.idm.TipoDocumentoRepresentation;

public class RepresentationToModel {

	public static TipoDocumentoModel createTipoDocumento(TipoDocumentoRepresentation rep) {
		TipoDocumentoModel model = new TipoDocumentoAdapter(null, null);
		model.setAbreviatura(rep.getAbreviatura());
		model.setDenominacion(rep.getDenominacion());
		model.setTipoPersona(TipoPersona.valueOf(rep.getTipoPersona().toUpperCase()));
		model.setCantidadCaracteres(rep.getCantidadCaracteres());

		//return provider.addTipoDocumento(model);
		return null;
	}

	public static PersonaNaturalModel createPersonaNatural(PersonaNaturalRepresentation personaNaturalRepresentation) {
		return null;
	}

	public static PersonaJuridicaModel createPersonaJuridica(PersonaJuridicaRepresentation personaJuridicaRepresentation) {
		return null;
	}

}
