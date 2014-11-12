package org.softgreen.sistcoop.ubigeo.restapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.softgreen.sistcoop.ubigeo.client.models.DepartamentoModel;
import org.softgreen.sistcoop.ubigeo.client.models.DistritoModel;
import org.softgreen.sistcoop.ubigeo.client.models.ProvinciaModel;
import org.softgreen.sistcoop.ubigeo.client.models.UbigeoProvider;
import org.softgreen.sistcoop.ubigeo.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.ubigeo.client.representations.idm.DepartamentoRepresentation;
import org.softgreen.sistcoop.ubigeo.client.representations.idm.DistritoRepresentation;
import org.softgreen.sistcoop.ubigeo.client.representations.idm.ProvinciaRepresentation;
import org.softgreen.sistcoop.ubigeo.restapi.representation.DepartamentoList;
import org.softgreen.sistcoop.ubigeo.restapi.representation.DistritoList;
import org.softgreen.sistcoop.ubigeo.restapi.representation.ProvinciaList;

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

	@GET
	@Path("/{codigo}/provincias")
	@Produces({ "application/xml", "application/json" })
	public ProvinciaList getProvincias(@PathParam("codigo") String codigoDepartamento) {
		List<ProvinciaModel> list = ubigeoProvider.getProvincias(codigoDepartamento);
		List<ProvinciaRepresentation> result = new ArrayList<ProvinciaRepresentation>();
		for (ProvinciaModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return new ProvinciaList(result);
	}

	@GET
	@Path("/{codigoDep}/provincias/{codigoProv}/distritos")
	@Produces({ "application/xml", "application/json" })
	public DistritoList getDistritos(@PathParam("codigoDep") String codigoDepartamento, @PathParam("codigoProv") String codigoProvincia) {
		List<DistritoModel> list = ubigeoProvider.getDistritos(codigoDepartamento, codigoProvincia);
		List<DistritoRepresentation> result = new ArrayList<DistritoRepresentation>();
		for (DistritoModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return new DistritoList(result);
	}
}