package org.softgreen.sistcoop.ubigeo.restapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.softgreen.sistcoop.ubigeo.client.models.CurrencyModel;
import org.softgreen.sistcoop.ubigeo.client.models.CurrencyProvider;
import org.softgreen.sistcoop.ubigeo.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.ubigeo.client.representations.idm.CurrencyRepresentation;

@Path("/currencies")
@Stateless
public class CurrencyResource {

	@Inject
	protected CurrencyProvider currencyProvider;

	@GET
	@Produces({ "application/xml", "application/json" })
	public List<CurrencyRepresentation> findAll() {
		List<CurrencyModel> list = currencyProvider.findAll();
		List<CurrencyRepresentation> result = new ArrayList<CurrencyRepresentation>();
		for (CurrencyModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return result;
	}

}