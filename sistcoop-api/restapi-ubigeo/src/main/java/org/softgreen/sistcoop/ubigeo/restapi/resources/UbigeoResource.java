package org.softgreen.sistcoop.ubigeo.restapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.softgreen.sistcoop.ubigeo.client.models.DepartamentoModel;
import org.softgreen.sistcoop.ubigeo.client.models.UbigeoProvider;
import org.softgreen.sistcoop.ubigeo.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.ubigeo.client.representations.idm.DepartamentoRepresentation;
import org.softgreen.sistcoop.ubigeo.restapi.representation.DepartamentoList;

@Path("/departamentos")
@Stateless
public class UbigeoResource {

	@Inject
	protected UbigeoProvider ubigeoProvider;

	@GET
	@Produces({ "application/xml", "application/json" })
	public DepartamentoList findAll() {
		List<DepartamentoModel> list = ubigeoProvider.getDepartamentos();
		List<DepartamentoRepresentation> result = new ArrayList<DepartamentoRepresentation>();
		for (DepartamentoModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return new DepartamentoList(result);
	}

}