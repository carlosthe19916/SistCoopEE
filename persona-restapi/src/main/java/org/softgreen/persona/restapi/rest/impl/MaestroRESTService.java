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
package org.softgreen.persona.restapi.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

import org.softgreen.persona.entity.type.EstadoCivil;
import org.softgreen.persona.entity.type.Sexo;
import org.softgreen.persona.entity.type.TipoEmpresa;
import org.softgreen.persona.entity.type.TipoPersona;
import org.softgreen.persona.restapi.rest.JsonAPI;
import org.softgreen.persona.restapi.rest.MaestroREST;
import org.softgreen.persona.service.MaestroService;

public class MaestroRESTService implements MaestroREST {

	@EJB
	private MaestroService maestroService;

	@Override
	public Response getTipoPersonas() {
		Response response;
		TipoPersona[] s = TipoPersona.values();
		List<TipoPersona> list = new ArrayList<TipoPersona>();
		for (int i = 0; i < s.length; i++) {
			list.add(s[i]);
		}
		JsonAPI api = new JsonAPI();
		api.setData(list);
		response = Response.status(Response.Status.OK).entity(api).build();
		return response;
	}

	@Override
	public Response getEstadosCiviles() {
		Response response;
		EstadoCivil[] e = EstadoCivil.values();
		List<EstadoCivil> list = new ArrayList<EstadoCivil>();
		for (int i = 0; i < e.length; i++) {
			list.add(e[i]);
		}
		JsonAPI api = new JsonAPI();
		api.setData(list);
		response = Response.status(Response.Status.OK).entity(api).build();
		return response;
	}

	@Override
	public Response getSexos() {
		Response response;
		Sexo[] s = Sexo.values();
		List<Sexo> list = new ArrayList<Sexo>();
		for (int i = 0; i < s.length; i++) {
			list.add(s[i]);
		}
		JsonAPI api = new JsonAPI();
		api.setData(list);
		response = Response.status(Response.Status.OK).entity(api).build();
		return response;
	}

	@Override
	public Response getTiposEmpresa() {
		Response response;
		TipoEmpresa[] s = TipoEmpresa.values();
		List<TipoEmpresa> list = new ArrayList<TipoEmpresa>();
		for (int i = 0; i < s.length; i++) {
			list.add(s[i]);
		}
		JsonAPI api = new JsonAPI();
		api.setData(list);
		response = Response.status(Response.Status.OK).entity(api).build();
		return response;
	}

}
