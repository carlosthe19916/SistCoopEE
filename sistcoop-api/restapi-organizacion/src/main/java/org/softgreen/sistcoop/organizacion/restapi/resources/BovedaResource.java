package org.softgreen.sistcoop.organizacion.restapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaProvider;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.CajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.DetalleHistorialRepresentation;
import org.softgreen.sistcoop.organizacion.managers.BovedaManager;

@Path("/bovedas")
@Stateless
public class BovedaResource {

	@Inject
	protected BovedaProvider bovedaProvider;

	@Inject
	protected BovedaManager bovedaManager;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public BovedaRepresentation getBovedaById(@PathParam("id") Integer id) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		BovedaRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@GET
	@Path("/{id}/cajas")
	@Produces({ "application/xml", "application/json" })
	public List<CajaRepresentation> getCajasAsignadas(@PathParam("id") Integer id) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		List<BovedaCajaModel> bovedaCajaList = model.getBovedaCajas();
		List<CajaRepresentation> list = new ArrayList<CajaRepresentation>();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaList) {
			CajaModel cajaModel = bovedaCajaModel.getCaja();
			CajaRepresentation cajaRepresentation = new CajaRepresentation();
			cajaRepresentation.setId(cajaModel.getId());
			cajaRepresentation.setDenominacion(cajaModel.getDenominacion());
			cajaRepresentation.setAbierto(cajaModel.isAbierto());
			cajaRepresentation.setEstadoMovimiento(cajaModel.getEstadoMovimiento());
			cajaRepresentation.setEstado(cajaModel.getEstado());

			list.add(cajaRepresentation);
		}
		return list;
	}

	@GET
	@Path("/{id}/detalle")
	@Produces({ "application/xml", "application/json" })
	public List<DetalleHistorialRepresentation> getDetalle(@PathParam("id") Integer id) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		HistorialModel historialModel = model.getHistorialActivo();
		if (historialModel != null) {
			List<DetalleHistorialModel> detalleHistorialModel = historialModel.getDetalle();
			List<DetalleHistorialRepresentation> result = new ArrayList<DetalleHistorialRepresentation>();
			for (DetalleHistorialModel detHistModel : detalleHistorialModel) {
				DetalleHistorialRepresentation detalleHistorialRepresentation = ModelToRepresentation.toRepresentation(detHistModel);
				result.add(detalleHistorialRepresentation);
			}
			return result;
		} else {
			return null;
		}
	}	

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public void update(@PathParam("id") Integer id, BovedaRepresentation rep) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		model.setDenominacion(rep.getDenominacion());
		model.commit();
	}

	@POST
	@Path("/{id}/desactivar")
	@Produces({ "application/xml", "application/json" })
	public void desactivar(@PathParam("id") Integer id) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		if (model == null) {
			throw new NotFoundException("Boveda not found.");
		}
		bovedaManager.desactivarBoveda(model);
	}

	@POST
	@Path("/{id}/abrir")
	@Produces({ "application/xml", "application/json" })
	public void abrir(@PathParam("id") Integer id) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		if (model == null) {
			throw new NotFoundException("Boveda not found.");
		}
		bovedaManager.abrir(model);
	}

	@POST
	@Path("/{id}/cerrar")
	@Produces({ "application/xml", "application/json" })
	public void cerrar(@PathParam("id") Integer id) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		if (model == null) {
			throw new NotFoundException("Boveda not found.");
		}
		bovedaManager.cerrar(model);
	}

	@POST
	@Path("/{id}/congelar")
	@Produces({ "application/xml", "application/json" })
	public void congelar(@PathParam("id") Integer id) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		if (model == null) {
			throw new NotFoundException("Boveda not found.");
		}
		bovedaManager.congelar(model);
	}

	@POST
	@Path("/{id}/descongelar")
	@Produces({ "application/xml", "application/json" })
	public void descongelar(@PathParam("id") Integer id) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		if (model == null) {
			throw new NotFoundException("Boveda not found.");
		}
		bovedaManager.descongelar(model);
	}
}