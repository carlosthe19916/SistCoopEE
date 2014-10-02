package org.softgreen.sistcoop.persona.client.models.util;

import org.softgreen.sistcoop.persona.client.models.CountryModel;
import org.softgreen.sistcoop.persona.client.representations.idm.CountryRepresentation;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class ModelToRepresentation {

	public static CountryRepresentation toRepresentation(CountryModel model) {
		if (model == null)
			return null;
		CountryRepresentation rep = new CountryRepresentation();

		rep.setAlpha2Code(model.getAlpha2Code());
		rep.setAlpha3Code(model.getAlpha3Code());
		rep.setFullName(model.getFullName());
		rep.setIndependent(model.isIndependent());
		rep.setNumericCode(model.getNumericCode());
		rep.setRemarks(model.getRemarks());
		rep.setShortName(model.getShortName());
		rep.setShortNameLowerCase(model.getShortNameLowerCase());
		rep.setStatus(model.getStatus());
		rep.setTerritoryName(model.getTerritoryName());

		return rep;
	}

}
