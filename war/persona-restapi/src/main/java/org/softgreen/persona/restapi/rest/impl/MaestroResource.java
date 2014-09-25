package org.softgreen.persona.restapi.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.softgreen.persona.model.type.EstadoCivil;
import org.softgreen.persona.model.type.Sexo;
import org.softgreen.persona.model.type.TipoEmpresa;
import org.softgreen.persona.model.type.TipoPersona;

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
