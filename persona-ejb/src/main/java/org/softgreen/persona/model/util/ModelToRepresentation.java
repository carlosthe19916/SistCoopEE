package org.softgreen.persona.model.util;

import org.softgreen.persona.model.AccionistaModel;
import org.softgreen.persona.model.PersonaJuridicaModel;
import org.softgreen.persona.model.PersonaNaturalModel;
import org.softgreen.persona.model.TipoDocumentoModel;
import org.softgreen.persona.representation.idm.AccionistaRepresentation;
import org.softgreen.persona.representation.idm.PersonaJuridicaRepresentation;
import org.softgreen.persona.representation.idm.PersonaNaturalRepresentation;
import org.softgreen.persona.representation.idm.TipoDocumentoRepresentation;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class ModelToRepresentation {

	public static TipoDocumentoModel toRepresentation(
			TipoDocumentoRepresentation persona) {
		return null;
	}
	
	public static PersonaNaturalRepresentation toRepresentation(
			PersonaNaturalModel persona) {
		PersonaNaturalRepresentation rep = new PersonaNaturalRepresentation();
		return rep;
	}
	
	public static PersonaJuridicaRepresentation toRepresentation(
			PersonaJuridicaModel persona) {
		return null;
	}
	
	public static AccionistaRepresentation toRepresentation(
			AccionistaModel persona) {
		return null;
	}

}
