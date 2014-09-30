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
package org.softgreen.persona.restapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.softgreen.sistcoop.persona.enums.EstadoCivil;
import org.softgreen.sistcoop.persona.enums.Sexo;
import org.softgreen.sistcoop.persona.enums.TipoEmpresa;
import org.softgreen.sistcoop.persona.enums.TipoPersona;

@Path("/")
public class MaestroResource {

	@GET
	@Path("/tipoPersonas")
	@Produces({ "application/xml", "application/json" })
	public Response getTipoPersonas() {
		Response response;
		TipoPersona[] s = TipoPersona.values();
		List<TipoPersona> list = new ArrayList<TipoPersona>();
		for (int i = 0; i < s.length; i++) {
			list.add(s[i]);
		}
		response = Response.status(Response.Status.OK).entity(list).build();
		return response;
	}

	@GET
	@Path("/estadosCiviles")
	@Produces({ "application/xml", "application/json" })
	public Response getEstadosCiviles() {
		Response response;
		EstadoCivil[] e = EstadoCivil.values();
		List<EstadoCivil> list = new ArrayList<EstadoCivil>();
		for (int i = 0; i < e.length; i++) {
			list.add(e[i]);
		}
		response = Response.status(Response.Status.OK).entity(list).build();
		return response;
	}

	@GET
	@Path("/sexos")
	@Produces({ "application/xml", "application/json" })
	public Response getSexos() {
		Response response;
		Sexo[] s = Sexo.values();
		List<Sexo> list = new ArrayList<Sexo>();
		for (int i = 0; i < s.length; i++) {
			list.add(s[i]);
		}
		response = Response.status(Response.Status.OK).entity(list).build();
		return response;
	}

	@GET
	@Path("/tiposEmpresa")
	@Produces({ "application/xml", "application/json" })
	public Response getTiposEmpresa() {
		Response response;
		TipoEmpresa[] s = TipoEmpresa.values();
		List<TipoEmpresa> list = new ArrayList<TipoEmpresa>();
		for (int i = 0; i < s.length; i++) {
			list.add(s[i]);
		}
		response = Response.status(Response.Status.OK).entity(list).build();
		return response;
	}

}
