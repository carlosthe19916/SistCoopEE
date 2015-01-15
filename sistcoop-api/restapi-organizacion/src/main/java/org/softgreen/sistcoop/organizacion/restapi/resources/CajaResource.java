package org.softgreen.sistcoop.organizacion.restapi.resources;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaProvider;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.CajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.DetalleHistorialRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.TrabajadorRepresentation;
import org.softgreen.sistcoop.organizacion.managers.CajaManager;
import org.softgreen.sistcoop.organizacion.restapi.config.Jsend;
import org.softgreen.sistcoop.organizacion.restapi.representation.BovedaList;
import org.softgreen.sistcoop.organizacion.restapi.representation.DetalleHistorialCajaList;
import org.softgreen.sistcoop.organizacion.restapi.representation.DetalleHistorialCajaRepresentation;
import org.softgreen.sistcoop.organizacion.restapi.representation.TrabajadorList;

@Path("/cajas")
@Stateless
public class CajaResource {

	@Inject
	protected BovedaProvider bovedaProvider;

	@Inject
	protected BovedaCajaProvider bovedaCajaProvider;

	@Inject
	protected CajaProvider cajaProvider;

	@Inject
	protected CajaManager cajaManager;

	@Inject
	protected RepresentationToModel representationToModel;

	@Context
	protected UriInfo uriInfo;

	@BadgerFish
	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public CajaRepresentation getCajaById(@PathParam("id") Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		CajaRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@GET
	@Path("/{id}/bovedas")
	@Produces({ "application/xml", "application/json" })
	public BovedaList getBovedasAsignadas(@PathParam("id") Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		List<BovedaCajaModel> bovedaCajaList = model.getBovedaCajas();
		List<BovedaRepresentation> list = new ArrayList<BovedaRepresentation>();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaList) {
			BovedaModel bovedaModel = bovedaCajaModel.getBoveda();
			BovedaRepresentation bovedaRepresentation = new BovedaRepresentation();
			bovedaRepresentation.setId(bovedaModel.getId());
			bovedaRepresentation.setMoneda(bovedaModel.getMoneda());
			bovedaRepresentation.setDenominacion(bovedaModel.getDenominacion());
			bovedaRepresentation.setAbierto(bovedaModel.isAbierto());
			bovedaRepresentation.setEstadoMovimiento(bovedaModel.getEstadoMovimiento());
			bovedaRepresentation.setEstado(bovedaModel.getEstado());

			list.add(bovedaRepresentation);
		}
		return new BovedaList(list);
	}

	@GET
	@Path("/{id}/trabajadores")
	@Produces({ "application/xml", "application/json" })
	public TrabajadorList getTrabajadoresAsignados(@PathParam("id") Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		List<TrabajadorCajaModel> trabajadorCajaList = model.getTrabajadorCajas();
		List<TrabajadorRepresentation> list = new ArrayList<TrabajadorRepresentation>();
		for (TrabajadorCajaModel trabajadorCajaModel : trabajadorCajaList) {
			TrabajadorModel trabajadorModel = trabajadorCajaModel.getTrabajador();
			TrabajadorRepresentation trabajadorRepresentation = new TrabajadorRepresentation();
			trabajadorRepresentation.setId(trabajadorModel.getId());
			trabajadorRepresentation.setTipoDocumento(trabajadorModel.getTipoDocumento());
			trabajadorRepresentation.setNumeroDocumento(trabajadorModel.getNumeroDocumento());
			trabajadorRepresentation.setUsuario(trabajadorModel.getUsuario());
			trabajadorRepresentation.setEstado(trabajadorModel.getEstado());

			list.add(trabajadorRepresentation);
		}
		return new TrabajadorList(list);
	}

	@GET
	@Path("/{id}/detalle")
	@Produces({ "application/xml", "application/json" })
	public DetalleHistorialCajaList getDetalle(@PathParam("id") Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		List<BovedaCajaModel> bovedaCajaModels = model.getBovedaCajas();

		List<HistorialModel> historialesActivos = new ArrayList<HistorialModel>();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
			HistorialModel historialModel = bovedaCajaModel.getHistorialActivo();
			if (historialModel != null)
				historialesActivos.add(historialModel);
		}

		boolean firstTime;
		if (historialesActivos.size() == 0) {
			firstTime = true;
		} else if (historialesActivos.size() == bovedaCajaModels.size()) {
			firstTime = false;
		} else {
			throw new EJBException("Error interno, existen cajas que no tienen historiales. Pongase en contacto con el area de sistemas.");
		}

		if (firstTime) {
			return null;
		} else {
			List<DetalleHistorialCajaRepresentation> result = new ArrayList<DetalleHistorialCajaRepresentation>();
			for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
				BovedaModel bovedaModel = bovedaCajaModel.getBoveda();
				HistorialModel historialModel = bovedaCajaModel.getHistorialActivo();
				List<DetalleHistorialModel> detalleHistorialModels = historialModel.getDetalle();

				List<DetalleHistorialRepresentation> detalleHistorialRepresentations = new ArrayList<DetalleHistorialRepresentation>();
				for (DetalleHistorialModel detalleHistorialModel : detalleHistorialModels) {
					int cantidad = detalleHistorialModel.getCantidad();
					BigDecimal valor = detalleHistorialModel.getValor();

					DetalleHistorialRepresentation detalleHistorialRepresentation = new DetalleHistorialRepresentation();
					detalleHistorialRepresentation.setCantidad(cantidad);
					detalleHistorialRepresentation.setValor(valor);

					detalleHistorialRepresentations.add(detalleHistorialRepresentation);
				}

				DetalleHistorialCajaRepresentation detalleHistorialCajaRepresentation = new DetalleHistorialCajaRepresentation(detalleHistorialRepresentations);
				detalleHistorialCajaRepresentation.setBoveda(ModelToRepresentation.toRepresentation(bovedaModel));
				result.add(detalleHistorialCajaRepresentation);
			}
			return new DetalleHistorialCajaList(result);
		}
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public void update(@PathParam("id") Integer id, CajaRepresentation rep) {
		CajaModel model = cajaProvider.getCajaById(id);
		model.setDenominacion(rep.getDenominacion());
		model.commit();
		model.commit();
	}

	@POST
	@Path("/{id}/desactivar")
	@Produces({ "application/xml", "application/json" })
	public void desactivar(@PathParam("id") Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		if (model == null) {
			throw new NotFoundException("Caja not found.");
		}
		cajaManager.desactivarCaja(model);
	}

	@POST
	@Path("/{id}/abrir")
	@Produces({ "application/xml", "application/json" })
	public void abrir(@PathParam("id") Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		if (model == null) {
			throw new NotFoundException("Caja not found.");
		}
		cajaManager.abrir(model);
	}

	@POST
	@Path("/{id}/cerrar")
	@Produces({ "application/xml", "application/json" })
	public void cerrar(@PathParam("id") Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		if (model == null) {
			throw new NotFoundException("Caja not found.");
		}
		cajaManager.cerrar(model);
	}

	@POST
	@Path("/{id}/bovedas")
	@Produces({ "application/xml", "application/json" })
	public Response addBoveda(@PathParam("id") Integer id, BovedaRepresentation bovedaRepresentation) {
		CajaModel model = cajaProvider.getCajaById(id);
		BovedaModel bovedaModel = bovedaProvider.getBovedaById(bovedaRepresentation.getId());
		if (model == null) {
			throw new NotFoundException("Caja not found.");
		}
		if (bovedaModel == null) {
			throw new NotFoundException("Boveda not found.");
		}

		BovedaCajaModel bovedaCajaModel = cajaManager.addBoveda(model, bovedaModel);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(bovedaCajaModel.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(bovedaCajaModel.getId())).build();
	}

	@POST
	@Path("/{id}/bovedas/{idBoveda}/desactivar")
	@Produces({ "application/xml", "application/json" })
	public void desactivarBovedaCaja(@PathParam("id") Integer id, @PathParam("idBoveda") Integer idBoveda) {
		CajaModel model = cajaProvider.getCajaById(id);
		BovedaModel bovedaModel = bovedaProvider.getBovedaById(idBoveda);
		if (model == null) {
			throw new NotFoundException("Caja not found.");
		}
		if (bovedaModel == null) {
			throw new NotFoundException("Boveda not found.");
		}
		if (model.isAbierto()) {
			throw new InternalServerErrorException("Caja abierta, debe cerrarla antes de desvincular boveda.");
		}
		if (bovedaModel.isAbierto()) {
			throw new InternalServerErrorException("Boveda abierta, debe cerrarla antes de desvincular boveda.");
		}

		BovedaCajaModel bovedaCajaModelToRemove = null;
		List<BovedaCajaModel> bovedaCajaModels = model.getBovedaCajas();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
			BovedaModel bovedaModel2 = bovedaCajaModel.getBoveda();
			if (bovedaModel.equals(bovedaModel2)) {
				bovedaCajaModelToRemove = bovedaCajaModel;
			}
		}

		if (bovedaCajaModelToRemove == null) {
			throw new NotFoundException("BovedaCaja not found.");
		}

		BigDecimal saldo = bovedaCajaModelToRemove.getSaldo();
		if (saldo.compareTo(BigDecimal.ZERO) != 0) {
			throw new InternalServerErrorException("Caja tiene saldo diferente de 0.");
		}

		bovedaCajaModelToRemove.setEstado(false);
		bovedaCajaModelToRemove.commit();
	}
}