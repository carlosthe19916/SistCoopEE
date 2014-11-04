package org.softgreen.sistcoop.persona.restapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.softgreen.sistcoop.persona.restapi.representation.CountryList;
import org.softgreen.sistcoop.ubigeo.client.models.CountryModel;
import org.softgreen.sistcoop.ubigeo.client.models.CountryProvider;
import org.softgreen.sistcoop.ubigeo.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.ubigeo.client.representations.idm.CountryRepresentation;

@Path("/paises")
@Stateless
public class TipoDocumentoResource {

	@Inject
	protected CountryProvider countryProvider;

	@GET
	@Produces({ "application/xml", "application/json" })
	public CountryList findAll() {
		List<CountryModel> list = countryProvider.findAll();
		List<CountryRepresentation> result = new ArrayList<CountryRepresentation>();
		for (CountryModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return new CountryList(result);
	}

}