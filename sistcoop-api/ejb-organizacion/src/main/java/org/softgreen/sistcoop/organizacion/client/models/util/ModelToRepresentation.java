package org.softgreen.sistcoop.organizacion.client.models.util;

import org.softgreen.sistcoop.organizacion.client.models.SucursalModel;
import org.softgreen.sistcoop.organizacion.client.representations.idm.SucursalRepresentation;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class ModelToRepresentation {

	public static SucursalRepresentation toRepresentation(SucursalModel model) {
		if (model == null)
			return null;
		SucursalRepresentation rep = new SucursalRepresentation();

		rep.setId(model.getId());
		rep.setAbreviatura(model.getAbreviatura());
		rep.setDenominacion(model.getDenominacion());		
		rep.setEstado(model.getEstado());

		return rep;
	}

}
