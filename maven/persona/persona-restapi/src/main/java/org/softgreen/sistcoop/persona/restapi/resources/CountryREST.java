/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.softgreen.sistcoop.persona.restapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.softgreen.sistcoop.persona.client.models.CountryModel;
import org.softgreen.sistcoop.persona.client.models.CountryProvider;
import org.softgreen.sistcoop.persona.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.persona.client.representations.idm.CountryRepresentation;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the
 * members table.
 */
@Path("/countries")
public class CountryREST {

	@Inject
	private CountryProvider countryProvider;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAllMembers() {
		List<CountryModel> list = countryProvider.findAll();
		List<CountryRepresentation> result = new ArrayList<>();
		for (CountryModel countryModel : list) {
			result.add(ModelToRepresentation.toRepresentation(countryModel));
		}
		return Response.ok().entity(result).build();
	}

}
