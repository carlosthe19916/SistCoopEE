package org.softgreen.sistcoop.persona.ejb.models.jpa;

import java.util.Calendar;
import java.util.Date;
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
import org.softgreen.sistcoop.persona.clien.enums.Sexo;
import org.softgreen.sistcoop.persona.clien.enums.TipoPersona;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalProvider;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.persona.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.persona.client.representations.idm.PersonaNaturalRepresentation;
import org.softgreen.sistcoop.persona.client.representations.idm.TipoDocumentoRepresentation;

@RunWith(Arquillian.class)
public class JpaPersonaNaturalProviderTest {

	private final static Logger log = Logger.getLogger(JpaPersonaNaturalProviderTest.class.getName());

	@Inject
	TipoDocumentoProvider tipoDocumentoProvider;
	
	@Inject
	PersonaNaturalProvider personaNaturalProvider;	
	
	@Inject
	RepresentationToModel representationToModel;
	
	@Deployment
	public static Archive<?> createTestArchive() throws IllegalArgumentException, ClassNotFoundException {
		return ShrinkWrap.create(WebArchive.class, "test.war")
		.addPackage("org.softgreen.sistcoop.persona.clien.enums")		
		.addPackage("org.softgreen.sistcoop.persona.client.models")
		.addPackage("org.softgreen.sistcoop.persona.client.models.util")		
		.addPackage("org.softgreen.sistcoop.persona.client.providers")
		.addPackage("org.softgreen.sistcoop.persona.client.representations.idm")
		.addPackage("org.softgreen.sistcoop.persona.ejb.models.util")
		.addPackage("org.softgreen.sistcoop.persona.ejb.models.jpa")
		.addPackage("org.softgreen.sistcoop.persona.ejb.models.jpa.entities")
				
		/***/
		.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
		/** beans.xml **/
		.addAsWebInfResource(EmptyAsset.INSTANCE, "META-INF/beans.xml")
		/** deploy test DS **/
		.addAsWebInfResource("test-ds.xml");
	}	
	
	@Test
	@InSequence(1)
	public void addTipoDocumentoPN() throws Exception {		
		
		String codigoPais = "PER";
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.addTipoDocumento("DNI", "Documento Nacional de Identidad", 8, TipoPersona.NATURAL);
		String numeroDocumento = "46779354";
		String apellidoPaterno = "Feria";
		String apellidoMaterno = "Vila";
		String nombres = "Carlos";
		Date fechaNacimiento = Calendar.getInstance().getTime();
		Sexo sexo = Sexo.MASCULINO;
		
		PersonaNaturalModel model = personaNaturalProvider.addPersonaNatural(codigoPais, tipoDocumentoModel, numeroDocumento, apellidoPaterno, apellidoMaterno, nombres, fechaNacimiento, sexo);

		log.info(model.getId() + " creado");
	}

	@Test
	@InSequence(2)
	public void getPersonaNaturalById() throws Exception {		
		PersonaNaturalModel model = personaNaturalProvider.getPersonaNaturalById(1L);		
		log.info(model.getId() + " found");		
	}
	
	@Test
	@InSequence(3)
	public void getPersonaNaturalByTipoNumeroDoc() throws Exception {		
		TipoDocumentoModel tipoDocumento = tipoDocumentoProvider.getTipoDocumentoByAbreviatura("DNI");		
		PersonaNaturalModel model = personaNaturalProvider.getPersonaNaturalByTipoNumeroDoc(tipoDocumento, "46779354");
		log.info(model.getId() + " found");
	}

	@Test
	@InSequence(4)
	public void getPersonasNaturales() throws Exception {	
		List<PersonaNaturalModel> list = personaNaturalProvider.getPersonasNaturales();		
		log.info("getPersonasNaturales()" + " size:" + list.size());
	}
	
	@Test
	@InSequence(5)
	public void getPersonasNaturalesLimit() throws Exception {	
		List<PersonaNaturalModel> list = personaNaturalProvider.getPersonasNaturales(0, 10);		
		log.info("getPersonasNaturales()" + " size:" + list.size());
	}
	
	@Test
	@InSequence(6)
	public void getPersonasNaturalesCount() throws Exception {			
		int count = personaNaturalProvider.getPersonasNaturalesCount();;		
		log.info("count:"+ count);
	}
	
	@Test
	@InSequence(7)
	public void searchForFilterText() throws Exception {		
		List<PersonaNaturalModel> list = personaNaturalProvider.searchForFilterText("fer");		
		log.info("searchForFilterText():"+ list.size());
	}
	
	@Test
	@InSequence(8)
	public void searchForFilterTextLimit() throws Exception {		
		List<PersonaNaturalModel> list = personaNaturalProvider.searchForFilterText("fer", 0, 10);		
		log.info("searchForFilterText():"+ list.size());
	}
	
	@Test
	@InSequence(9)
	public void searchForNumeroDocumento() throws Exception {		
		List<PersonaNaturalModel> list = personaNaturalProvider.searchForNumeroDocumento("4677");		
		log.info("searchForNumeroDocumento():"+ list.size());
	}
	
	@Test
	@InSequence(10)
	public void searchForNumeroDocumentoLimit() throws Exception {		
		List<PersonaNaturalModel> list = personaNaturalProvider.searchForNumeroDocumento("4677", 0, 10);		
		log.info("searchForNumeroDocumento():"+ list.size());
	}
	
	@Test
	@InSequence(13)
	public void removePersonaNatural() throws Exception {	
		PersonaNaturalModel model = personaNaturalProvider.getPersonaNaturalById(1L);
		boolean result = personaNaturalProvider.removePersonaNatural(model);
		log.info("removePersonaNatural():"+ result);
	}
	
	@Test
	@InSequence(11)
	public void modelToRepresentation() {
		List<PersonaNaturalModel> list = personaNaturalProvider.getPersonasNaturales();
		if (list.size() < 1)
			log.log(Level.WARNING, "ModelToRepresentation() size = 0");
		for (PersonaNaturalModel model : list) {
			PersonaNaturalRepresentation representation = ModelToRepresentation.toRepresentation(model);
			log.info("Representation:" + representation.getId());
		}
	}

	@Test
	@InSequence(12)
	public void representationToModel() {
		PersonaNaturalRepresentation representation = new PersonaNaturalRepresentation();
		
		
		representation.setCodigoPais("PER");
		
		representation.setApellidoPaterno("FLORES");
		representation.setApellidoMaterno("HUERTAS");
		representation.setNombres("JHON");
		representation.setCelular("966350507");
		representation.setTelefono(null);
		representation.setEmail(null);
		
		representation.setEstadoCivil("soltero");
		representation.setFechaNacimiento(Calendar.getInstance().getTime());
		
		representation.setTipoDocumento("DNI");
		representation.setNumeroDocumento("12345678");
		representation.setSexo("masculino");		
				
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(representation.getTipoDocumento());
		
		PersonaNaturalModel model = representationToModel.createPersonaNatural(representation, tipoDocumentoModel, personaNaturalProvider);
		log.info("representationToModel:" + model.getId());
	}
}
