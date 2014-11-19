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
import org.softgreen.sistcoop.persona.clien.enums.TipoEmpresa;
import org.softgreen.sistcoop.persona.clien.enums.TipoPersona;
import org.softgreen.sistcoop.persona.client.models.PersonaJuridicaModel;
import org.softgreen.sistcoop.persona.client.models.PersonaJuridicaProvider;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalProvider;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.persona.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.persona.client.representations.idm.PersonaJuridicaRepresentation;
import org.softgreen.sistcoop.persona.client.representations.idm.PersonaNaturalRepresentation;

@RunWith(Arquillian.class)
public class PersonaJuridicaProviderTest {

	private final static Logger log = Logger.getLogger(PersonaJuridicaProviderTest.class.getName());

	@Inject
	TipoDocumentoProvider tipoDocumentoProvider;
	
	@Inject
	PersonaNaturalProvider personaNaturalProvider;	
	
	@Inject
	PersonaJuridicaProvider personaJuridicaProvider;
	
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
	public void add(){
		
		String codigoPais = "PER";
		TipoDocumentoModel tipoDocumentoRepresentante = tipoDocumentoProvider.addTipoDocumento("DNI", "Documento Nacional de Identidad", 8, TipoPersona.NATURAL);
		String numeroDocumento = "46779354";
		String apellidoPaterno = "Feria";
		String apellidoMaterno = "Vila";
		String nombres = "Carlos";
		Date fechaNacimiento = Calendar.getInstance().getTime();
		Sexo sexo = Sexo.MASCULINO;		
		PersonaNaturalModel representanteLegal = personaNaturalProvider.addPersonaNatural(codigoPais, tipoDocumentoRepresentante, numeroDocumento, apellidoPaterno, apellidoMaterno, nombres, fechaNacimiento, sexo);

		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.addTipoDocumento("RUC", "Registro Nacional de Contribuyentes", 8, TipoPersona.JURIDICA);
	
		PersonaJuridicaModel model = personaJuridicaProvider.addPersonaJuridica(
				representanteLegal, 
				"PE", 
				tipoDocumentoModel, 
				"12345678912", 
				"Softgreen S.A.C", 
				Calendar.getInstance().getTime(), 
				TipoEmpresa.PRIVADA, 
				true);
		
		log.info(model.getId() + " creado");		
	}
	

	@Test
	@InSequence(2)
	public void getById() throws Exception {		
		PersonaJuridicaModel model = personaJuridicaProvider.getPersonaJuridicaById(1L);		
		log.info(model.getId() + " found");		
	}
	
	@Test
	@InSequence(3)
	public void getByTipoNumeroDoc() throws Exception {		
		TipoDocumentoModel tipoDocumento = tipoDocumentoProvider.getTipoDocumentoByAbreviatura("RUC");		
		PersonaJuridicaModel model = personaJuridicaProvider.getPersonaJuridicaByTipoNumeroDoc(tipoDocumento, "12345678912");
		log.info(model.getId() + " found");
	}

	@Test
	@InSequence(4)
	public void getPersonas() throws Exception {	
		List<PersonaJuridicaModel> list = personaJuridicaProvider.getPersonasJuridicas();		
		log.info("getPersonasJuridicas()" + " size:" + list.size());
	}
	
	@Test
	@InSequence(5)
	public void getPersonasLimit() throws Exception {	
		List<PersonaJuridicaModel> list = personaJuridicaProvider.getPersonasJuridicas(0, 10);		
		log.info("getPersonasJuridicas()" + " size:" + list.size());
	}
	
	@Test
	@InSequence(6)
	public void getPersonasCount() throws Exception {			
		int count = personaJuridicaProvider.getPersonasJuridicasCount();		
		log.info("count:"+ count);
	}
	
	@Test
	@InSequence(7)
	public void searchForFilterText() throws Exception {		
		List<PersonaJuridicaModel> list = personaJuridicaProvider.searchForFilterText("sof");		
		log.info("searchForFilterText():"+ list.size());
	}
	
	@Test
	@InSequence(8)
	public void searchForFilterTextLimit() throws Exception {		
		List<PersonaJuridicaModel> list = personaJuridicaProvider.searchForFilterText("soft", 0, 10);		
		log.info("searchForFilterTextLimit():"+ list.size());
	}
	
	@Test
	@InSequence(9)
	public void searchForNumeroDocumento() throws Exception {		
		List<PersonaJuridicaModel> list = personaJuridicaProvider.searchForNumeroDocumento("123");		
		log.info("searchForNumeroDocumento():"+ list.size());
	}
	
	@Test
	@InSequence(10)
	public void searchForNumeroDocumentoLimit() throws Exception {		
		List<PersonaJuridicaModel> list = personaJuridicaProvider.searchForNumeroDocumento("123", 0, 10);		
		log.info("searchForNumeroDocumentoLimit():"+ list.size());
	}	
	
	@Test
	@InSequence(11)
	public void modelToRepresentation() {
		List<PersonaJuridicaModel> list = personaJuridicaProvider.getPersonasJuridicas();
		if (list.size() < 1)
			log.log(Level.WARNING, "ModelToRepresentation() size = 0");
		for (PersonaJuridicaModel model : list) {
			PersonaJuridicaRepresentation representation = ModelToRepresentation.toRepresentation(model);
			log.info("Representation:" + representation.getId());
		}
	}

	@Test
	@InSequence(12)
	public void representationToModel() {
		PersonaJuridicaRepresentation representation = new PersonaJuridicaRepresentation();
		
		
		representation.setCodigoPais("PER");
		
		representation.setRazonSocial("Sistcoop sac");
		representation.setNombreComercial("sistcop");
		representation.setActividadPrincipal("voladdura de rocas");
		representation.setFechaConstitucion(Calendar.getInstance().getTime());
		representation.setCelular("966350507");
		representation.setTelefono(null);
		representation.setEmail(null);
		
		representation.setTipoEmpresa(TipoEmpresa.PRIVADA.toString());
		
		representation.setTipoDocumento("RUC");
		representation.setNumeroDocumento("11111111111");	

		//representation.setNumeroDocumentoRepresentanteLegal("46779354");
		//representation.setTipoDocumentoRepresentanteLegal("DNI");
		
		/*PersonaNaturalModel representanteLegal = personaNaturalProvider.getPersonaNaturalByTipoNumeroDoc(
				tipoDocumentoProvider.getTipoDocumentoByAbreviatura(representation.getTipoDocumentoRepresentanteLegal()), 
				representation.getNumeroDocumentoRepresentanteLegal());
				
		PersonaJuridicaModel model = representationToModel.createPersonaJuridica(
				representation, 
				tipoDocumentoProvider.getTipoDocumentoByAbreviatura(representation.getTipoDocumento()),
				representanteLegal, 
				personaJuridicaProvider);*/
		
		//log.info("representationToModel:" + model.getId());
	}	
	
	@Test
	@InSequence(13)
	public void removePersonaNatural() throws Exception {	
		PersonaJuridicaModel model = personaJuridicaProvider.getPersonaJuridicaById(1L);
		boolean result = personaJuridicaProvider.removePersonaJuridica(model);
		log.info("removePersonaJuridica():"+ result);
	}
}
