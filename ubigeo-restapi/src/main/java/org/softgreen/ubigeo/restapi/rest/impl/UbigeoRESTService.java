package org.softgreen.ubigeo.restapi.rest.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.softgreen.ubigeo.entity.Departamento;
import org.softgreen.ubigeo.entity.Distrito;
import org.softgreen.ubigeo.entity.Provincia;
import org.softgreen.ubigeo.restapi.rest.JsonAPI;
import org.softgreen.ubigeo.restapi.rest.UbigeoREST;
import org.softgreen.ubigeo.service.UbigeoService;

public class UbigeoRESTService implements UbigeoREST {

	@EJB
	private UbigeoService ubigeoService;

	@Override
	public Response findAll() {
		List<Departamento> list = ubigeoService.getDepartamentos();
		JsonAPI api = new JsonAPI();
		api.setData(list);
		Response response = Response.status(Status.OK).entity(api).build();		
		return response;
	}

	@Override
	public Response getProvincias(String codigoDepartamento) {
		List<Provincia> list = ubigeoService.getProvincias(codigoDepartamento);
		Response response = Response.status(Status.OK).entity(list).build();
		return response;
	}

	@Override
	public Response getDistritos(String codigoDepartamento, String codigoProvincia) {
		List<Distrito> list = ubigeoService.getDistritos(codigoDepartamento, codigoProvincia);
		Response response = Response.status(Status.OK).entity(list).build();
		return response;
	}

}
