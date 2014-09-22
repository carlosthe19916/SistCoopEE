package org.softgreen.persona.restapi.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.softgreen.persona.manager.TipoDocumentoManager;
import org.softgreen.persona.model.TipoDocumentoModel;
import org.softgreen.persona.model.type.TipoPersona;
import org.softgreen.persona.model.util.ModelToRepresentation;
import org.softgreen.persona.model.util.RepresentationToModel;
import org.softgreen.persona.provider.TipoDocumentoProvider;
import org.softgreen.persona.representation.idm.TipoDocumentoRepresentation;
import org.softgreen.persona.restapi.rest.TipoDocumentoREST;

public class TipoDocumentoRESTService implements TipoDocumentoREST {

	@EJB
	protected TipoDocumentoProvider tipoDocumentoProvider;

	@Context
	protected UriInfo uriInfo;

	@Override
	public Response findAll(String tipoPersona) {
		List<TipoDocumentoModel> list = null;
		if (tipoPersona != null) {
			TipoPersona personType = TipoPersona.valueOf(tipoPersona);
			if (personType != null) {
				list = tipoDocumentoProvider.getTiposDocumento(TipoPersona.valueOf(tipoPersona));
			} else {
				list = tipoDocumentoProvider.getTiposDocumento();
			}
		} else {
			list = tipoDocumentoProvider.getTiposDocumento();
		}

		List<TipoDocumentoRepresentation> result = new ArrayList<TipoDocumentoRepresentation>();
		for (TipoDocumentoModel model : list) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return Response.ok(result).build();
	}

	@Override
	public Response findById(String id) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(id);
		TipoDocumentoRepresentation tipoDocumentoRepresentation = ModelToRepresentation.toRepresentation(tipoDocumentoModel);
		return Response.ok().entity(tipoDocumentoRepresentation).build();
	}

	@Override
	public Response create(TipoDocumentoRepresentation tipoDocumentoRepresentation) {
		TipoDocumentoModel tipoDocumentoModel = RepresentationToModel.createTipoDocumento(tipoDocumentoRepresentation);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(tipoDocumentoModel.getAbreviatura()).build()).build();
	}

	@Override
	public Response update(String id, TipoDocumentoRepresentation tipoDocumentoRepresentation) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(id);
		tipoDocumentoModel.setDenominacion(tipoDocumentoRepresentation.getDenominacion());
		tipoDocumentoModel.setTipoPersona(TipoPersona.valueOf(tipoDocumentoRepresentation.getTipoPersona().toUpperCase()));
		tipoDocumentoRepresentation.setCantidadCaracteres(tipoDocumentoRepresentation.getCantidadCaracteres());
		tipoDocumentoModel.commit();
		return Response.noContent().build();
	}

	@Override
	public Response delete(String id) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(id);
		if (tipoDocumentoModel != null) {
			boolean removed = tipoDocumentoProvider.removeTipoDocumento(tipoDocumentoModel);
			if (removed)
				return Response.noContent().build();
			else
				return Response.serverError().build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}