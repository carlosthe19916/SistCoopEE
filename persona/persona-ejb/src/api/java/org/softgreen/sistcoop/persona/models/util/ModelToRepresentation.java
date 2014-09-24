package org.softgreen.sistcoop.persona.models.util;

import org.softgreen.sistcoop.persona.models.AccionistaModel;
import org.softgreen.sistcoop.persona.models.PersonaJuridicaModel;
import org.softgreen.sistcoop.persona.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.representations.idm.AccionistaRepresentation;
import org.softgreen.sistcoop.persona.representations.idm.PersonaJuridicaRepresentation;
import org.softgreen.sistcoop.persona.representations.idm.PersonaNaturalRepresentation;
import org.softgreen.sistcoop.persona.representations.idm.TipoDocumentoRepresentation;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class ModelToRepresentation {

	public static TipoDocumentoRepresentation toRepresentation(TipoDocumentoModel model) {
		if(model == null)
			return null;
		TipoDocumentoRepresentation rep = new TipoDocumentoRepresentation();
		rep.setAbreviatura(model.getAbreviatura());
		rep.setDenominacion(model.getDenominacion());
		rep.setCantidadCaracteres(model.getCantidadCaracteres());
		rep.setTipoPersona(model.getTipoPersona().toString().toLowerCase());
		return rep;
	}

	public static PersonaNaturalRepresentation toRepresentation(PersonaNaturalModel persona) {
		PersonaNaturalRepresentation rep = new PersonaNaturalRepresentation();
		return rep;
	}

	public static PersonaJuridicaRepresentation toRepresentation(PersonaJuridicaModel persona) {
		return null;
	}

	public static AccionistaRepresentation toRepresentation(AccionistaModel persona) {
		return null;
	}

}
