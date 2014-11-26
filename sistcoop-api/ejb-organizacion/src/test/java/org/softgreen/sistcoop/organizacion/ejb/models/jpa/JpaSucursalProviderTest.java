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
import org.softgreen.sistcoop.organizacion.client.models.SucursalModel;
import org.softgreen.sistcoop.organizacion.client.models.SucursalProvider;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.organizacion.client.representations.idm.SucursalRepresentation;

@RunWith(Arquillian.class)
public class JpaSucursalProviderTest {

	private final static Logger log = Logger.getLogger(JpaSucursalProviderTest.class.getName());

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
		SucursalModel model = sucursalProvider.addSucursal("AYAC", "Ayacucho");
	}

	@Test
	@InSequence(2)
	public void getById() throws Exception {
		SucursalModel model = sucursalProvider.getSucursalById(1);
		if (model == null)
			throw new Exception("getById() no encontrado");
	}	

	@Test
	@InSequence(3)
	public void getList() throws Exception {
		List<SucursalModel> listActive = sucursalProvider.getSucursales();
		List<SucursalModel> listNoActive = sucursalProvider.getSucursales(false);	
	}	

	@Test
	@InSequence(4)
	public void modelToRepresentation() {
		List<SucursalModel> list = sucursalProvider.getSucursales();
		if (list.size() < 1)
			log.log(Level.WARNING, "ModelToRepresentation() size = 0");
		for (SucursalModel model : list) {
			SucursalRepresentation representation = ModelToRepresentation.toRepresentation(model);
			log.info("Representation:" + representation.getId());
		}
	}

	@Test
	@InSequence(5)
	public void representationToModel() {
		SucursalRepresentation representation = new SucursalRepresentation();

		representation.setAbreviatura("Hua");
		representation.setDenominacion("Huanta");
		representation.setEstado(true);		

		SucursalModel model = representationToModel.createSucursal(representation, sucursalProvider);
		log.info("representationToModel:" + model.getId());
	}
	
	@Test
	@InSequence(6)
	public void remove() throws Exception {
		SucursalModel model = sucursalProvider.getSucursalById(1);
		boolean result = sucursalProvider.removeSucursal(model);
		log.info("remove():" + result);
	}
}
