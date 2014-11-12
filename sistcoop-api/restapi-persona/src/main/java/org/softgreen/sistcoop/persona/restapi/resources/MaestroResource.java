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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.softgreen.sistcoop.persona.clien.enums.EstadoCivil;
import org.softgreen.sistcoop.persona.clien.enums.Sexo;
import org.softgreen.sistcoop.persona.clien.enums.TipoEmpresa;
import org.softgreen.sistcoop.persona.clien.enums.TipoPersona;
import org.softgreen.sistcoop.persona.restapi.representation.EstadoCivilList;
import org.softgreen.sistcoop.persona.restapi.representation.EstadoCivilRepresentation;
import org.softgreen.sistcoop.persona.restapi.representation.SexoList;
import org.softgreen.sistcoop.persona.restapi.representation.SexoRepresentation;
import org.softgreen.sistcoop.persona.restapi.representation.TipoEmpresaList;
import org.softgreen.sistcoop.persona.restapi.representation.TipoEmpresaRepresentation;
import org.softgreen.sistcoop.persona.restapi.representation.TipoPersonaList;
import org.softgreen.sistcoop.persona.restapi.representation.TipoPersonaRepresentation;

@Path("/")
public class MaestroResource {

	@GET
	@Path("/tiposPersona")
	@Produces({ "application/xml", "application/json" })
	public TipoPersonaList getTipoPersonas() {
		TipoPersona[] s = TipoPersona.values();
		List<TipoPersonaRepresentation> list = new ArrayList<TipoPersonaRepresentation>();
		for (int i = 0; i < s.length; i++) {
			list.add(new TipoPersonaRepresentation(s[i].toString()));
		}
		return new TipoPersonaList(list);
	}

	@GET
	@Path("/estadosCiviles")
	@Produces({ "application/xml", "application/json" })
	public EstadoCivilList getEstadosCiviles() {
		EstadoCivil[] e = EstadoCivil.values();
		List<EstadoCivilRepresentation> list = new ArrayList<EstadoCivilRepresentation>();
		for (int i = 0; i < e.length; i++) {
			list.add(new EstadoCivilRepresentation(e[i].toString()));
		}
		return new EstadoCivilList(list);
	}

	@GET
	@Path("/sexos")
	@Produces({ "application/xml", "application/json" })
	public SexoList getSexos() {
		Sexo[] s = Sexo.values();
		List<SexoRepresentation> list = new ArrayList<SexoRepresentation>();
		for (int i = 0; i < s.length; i++) {
			list.add(new SexoRepresentation(s[i].toString()));
		}
		return new SexoList(list);
	}

	@GET
	@Path("/tiposEmpresa")
	@Produces({ "application/xml", "application/json" })
	public TipoEmpresaList getTiposEmpresa() {
		TipoEmpresa[] s = TipoEmpresa.values();
		List<TipoEmpresaRepresentation> list = new ArrayList<TipoEmpresaRepresentation>();
		for (int i = 0; i < s.length; i++) {
			list.add(new TipoEmpresaRepresentation(s[i].toString()));
		}
		return new TipoEmpresaList(list);
	}

}
