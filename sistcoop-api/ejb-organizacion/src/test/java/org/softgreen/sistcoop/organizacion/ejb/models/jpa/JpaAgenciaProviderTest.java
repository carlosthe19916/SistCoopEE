package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.AgenciaProvider;
import org.softgreen.sistcoop.organizacion.client.models.SucursalModel;
import org.softgreen.sistcoop.organizacion.client.models.SucursalProvider;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.organizacion.client.representations.idm.AgenciaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.SucursalRepresentation;

@RunWith(Arquillian.class)
public class JpaAgenciaProviderTest {

	private final static Logger log = Logger.getLogger(JpaAgenciaProviderTest.class.getName());

	@Inject
	AgenciaProvider agenciaProvider;
		
	@Inject
	SucursalProvider sucursalProvider;
	
	@Inject
	RepresentationToModel representationToModel;
	
	@Deployment
	public static Archive<?> createTestArchive() throws IllegalArgumentException, ClassNotFoundException {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage("org.softgreen.sistcoop.organizacion.clien.enums")
				.addPackage("org.softgreen.sistcoop.organizacion.client.models")
				.addPackage("org.softgreen.sistcoop.organizacion.client.models.util")
				.addPackage("org.softgreen.sistcoop.organizacion.client.providers")
				.addPackage("org.softgreen.sistcoop.organizacion.client.representations.idm")
				.addPackage("org.softgreen.sistcoop.organizacion.ejb.models.util")
				.addPackage("org.softgreen.sistcoop.organizacion.ejb.models.jpa")
				.addPackage("org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities")

				/***/
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				/** beans.xml **/
				.addAsWebInfResource(EmptyAsset.INSTANCE, "META-INF/beans.xml")
				/** deploy test DS **/
				.addAsWebInfResource("test-ds.xml");
	}

	@Test
	@InSequence(1)
	public void create() throws Exception {
		SucursalModel s = sucursalProvider.addSucursal("Pp", "Prueba agencia");
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(1);
		AgenciaModel model = agenciaProvider.addAgencia(sucursalModel, "01", "Ayac.", "Agencia Ayacucho", "050101");
	}

	@Test
	@InSequence(2)
	public void getById() throws Exception {
		AgenciaModel model = agenciaProvider.getAgenciaById(1);
		if (model == null)
			throw new Exception("getById() no encontrado");
	}	

	@Test
	@InSequence(4)
	public void modelToRepresentation() {
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(1);
		List<AgenciaModel> list = sucursalModel.getAgencias();
		if (list.size() < 1)
			log.log(Level.WARNING, "ModelToRepresentation() size = 0");
		for (AgenciaModel model : list) {
			AgenciaRepresentation representation = ModelToRepresentation.toRepresentation(model);
			log.info("Representation:" + representation.getId());
		}
	}

	@Test
	@InSequence(5)
	public void representationToModel() {
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(1);
				
		AgenciaRepresentation representation = new AgenciaRepresentation();

		representation.setAbreviatura("Prue");
		representation.setCodigo("02");
		representation.setDenominacion("Agencia de prueba");
		representation.setEstado(true);
		representation.setUbigeo("050101");

		AgenciaModel model = representationToModel.createAgencia(sucursalModel, representation, agenciaProvider);
		log.info("representationToModel:" + model.getId());
	}
	
	@Test
	@InSequence(6)
	public void remove() throws Exception {
		AgenciaModel model = agenciaProvider.getAgenciaById(1);
		boolean result = agenciaProvider.removeAgencia(model);
		log.info("remove():" + result);
	}
}
