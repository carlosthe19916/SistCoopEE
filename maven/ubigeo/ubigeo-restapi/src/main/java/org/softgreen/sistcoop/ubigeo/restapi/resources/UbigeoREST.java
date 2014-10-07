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
package org.softgreen.sistcoop.ubigeo.restapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the
 * members table.
 */
@Path("/")
public class UbigeoREST {

	@Inject
	private UbigeoProvider ubigeoProvider;

	@GET
	@Path("/departamentos")
	@Produces(MediaType.APPLICATION_JSON)
	public DepartamentoList getDepartamentos() {
		List<DepartamentoModel> list = ubigeoProvider.getDepartamentos();
		List<DepartamentoRepresentation> result = new ArrayList<>();
		for (DepartamentoModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return new DepartamentoList(result);
	}

	@GET
	@Path("/departamentos/{codigoDepartamento}/provincias")
	@Produces(MediaType.APPLICATION_JSON)
	public ProvinciaList getProvincias(@PathParam("codigoDepartamento") String codigoDepartamento) {
		List<ProvinciaModel> list = ubigeoProvider.getProvincias(codigoDepartamento);
		List<ProvinciaRepresentation> result = new ArrayList<>();
		for (ProvinciaModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return new ProvinciaList(result);
	}

	@GET
	@Path("/departamentos/{codigoDepartamento}/provincias/{codigoProvincia}/distritos")
	@Produces(MediaType.APPLICATION_JSON)
	public DistritoList getDistritos(@PathParam("codigoDepartamento") String codigoDepartamento, @PathParam("codigoProvincia") String codigoProvincia) {
		List<DistritoModel> list = ubigeoProvider.getDistritos(codigoDepartamento, codigoProvincia);
		List<DistritoRepresentation> result = new ArrayList<>();
		for (DistritoModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return new DistritoList(result);
	}
}
