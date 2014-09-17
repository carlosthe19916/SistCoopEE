package org.softgreen.ubigeo.restapi.rest.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.softgreen.ubigeo.entity.Country;
import org.softgreen.ubigeo.restapi.rest.CountryREST;
import org.softgreen.ubigeo.restapi.rest.JsonAPI;
import org.softgreen.ubigeo.service.CountryService;

public class CountryRESTService implements CountryREST {

	@EJB
	private CountryService countryService;

	@Override
	public Response listAllMembers() {
		List<Country> list = countryService.findAll();		
		Response response = Response.status(Status.OK).entity(JsonAPI.getSuccess(list)).build();
		return response;
	}

}
