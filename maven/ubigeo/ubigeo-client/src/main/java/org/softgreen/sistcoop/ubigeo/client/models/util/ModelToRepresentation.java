package org.softgreen.sistcoop.ubigeo.client.models.util;

import org.softgreen.sistcoop.ubigeo.client.models.CountryModel;
import org.softgreen.sistcoop.ubigeo.client.models.DepartamentoModel;
import org.softgreen.sistcoop.ubigeo.client.representations.idm.CountryRepresentation;
import org.softgreen.sistcoop.ubigeo.client.representations.idm.DepartamentoRepresentation;

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
	
	public static DepartamentoRepresentation toRepresentation(DepartamentoModel model) {
		if (model == null)
			return null;
		DepartamentoRepresentation rep = new DepartamentoRepresentation();

		rep.setId(model.getId());
		rep.setCodigo(model.getCodigo());
		rep.setDenominacion(model.getDenominacion());

		return rep;
	}

}
