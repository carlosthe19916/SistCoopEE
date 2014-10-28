package org.softgreen.sistcoop.persona.ejb.models.jpa;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softgreen.sistcoop.persona.clien.enums.EstadoCivil;
import org.softgreen.sistcoop.persona.clien.enums.Sexo;
import org.softgreen.sistcoop.persona.clien.enums.TipoEmpresa;
import org.softgreen.sistcoop.persona.clien.enums.TipoPersona;
import org.softgreen.sistcoop.persona.client.models.AccionistaModel;
import org.softgreen.sistcoop.persona.client.models.AccionistaProvider;
import org.softgreen.sistcoop.persona.client.models.PersonaJuridicaModel;
import org.softgreen.sistcoop.persona.client.models.PersonaJuridicaProvider;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalProvider;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.persona.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.persona.client.providers.Provider;
import org.softgreen.sistcoop.persona.client.representations.idm.AccionistaRepresentation;
import org.softgreen.sistcoop.persona.client.representations.idm.PersonaJuridicaRepresentation;
import org.softgreen.sistcoop.persona.client.representations.idm.PersonaNaturalRepresentation;
import org.softgreen.sistcoop.persona.client.representations.idm.TipoDocumentoRepresentation;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.AccionistaEntity;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.PersonaEntity;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.PersonaJuridicaEntity;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.PersonaNaturalEntity;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.TipoDocumentoEntity;

@RunWith(Arquillian.class)
public class JpaPersonaNaturalProviderTest {

	@EJB
	PersonaNaturalProvider personaNaturalProvider;
	
	@EJB
	TipoDocumentoProvider tipoDocumentoProvider;
	
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addClasses(
						Provider.class, 
						ModelToRepresentation.class,
						RepresentationToModel.class,
						
						/**Enums**/
						EstadoCivil.class,
						Sexo.class,
						TipoEmpresa.class,
						TipoPersona.class,
						
						/**Models**/
						TipoDocumentoModel.class,
						PersonaNaturalModel.class,
						PersonaJuridicaModel.class,
						AccionistaModel.class,
						
						/**Providers**/
						TipoDocumentoProvider.class,
						PersonaNaturalProvider.class,
						PersonaJuridicaProvider.class,
						AccionistaProvider.class,						
						
						/**Representation**/
						TipoDocumentoRepresentation.class,
						PersonaNaturalRepresentation.class,
						PersonaJuridicaRepresentation.class,
						AccionistaRepresentation.class,
						
						/**Adapters**/
						TipoDocumentoAdapter.class,						
						PersonaNaturalAdapter.class,						
						PersonaJuridicaAdapter.class,
						AccionistaAdapter.class,
						
						/**Jpa providers**/
						JpaTipoDocumentoProvider.class,
						JpaPersonaNaturalProvider.class,
						JpaPersonaJuridicaProvider.class,
						JpaAccionistaProvider.class,
						
						/**entities**/
						TipoDocumentoEntity.class,
						PersonaEntity.class,
						PersonaNaturalEntity.class,
						PersonaJuridicaEntity.class,
						AccionistaEntity.class)
						
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "META-INF/beans.xml")
				// Deploy our test datasource
				.addAsWebInfResource("test-ds.xml");
	}

	
	@Test
	public void findById() throws Exception {
		//PersonaNaturalModel model = personaNaturalProvider.getPersonaNaturalById(1L);
		System.out.println("Model searched:");
	}
	
	/*@Test
	public void findByTipoNumeroDoc() throws Exception {
		PersonaNaturalModel model = personaNaturalProvider.getPersonaNaturalByTipoNumeroDoc(null, "");
		System.out.println("Model searched:" + model);
	}
	
	@Test
	public void getPersonasNaturales() throws Exception {
		List<PersonaNaturalModel> models = personaNaturalProvider.getPersonasNaturales();
		System.out.println("Models searched:" + models);
	}
	
	@Test
	public void getPersonasNaturalesLimit() throws Exception {
		List<PersonaNaturalModel> models = personaNaturalProvider.getPersonasNaturales(0, 10);
		System.out.println("Models searched:" + models);
	}
	
	@Test
	public void count() throws Exception {
		int count = personaNaturalProvider.getPersonasNaturalesCount();
		System.out.println("Models count:" + count);
	}
	
	@Test
	public void addPersonaNatural() throws Exception {
		String pais = "PER";
		TipoDocumentoModel tipoDocumento;
		String numDoc = "46779354";
		String apePaterno = "Feria";
		String apeMaterno = "Vila";
		String nombres = "Carlos esteban";				
		Date fechNac;
		Sexo sexo = Sexo.MASCULINO;
		
		tipoDocumento = tipoDocumentoProvider.getTipoDocumentoByAbreviatura("DNI");
		Calendar calendar = Calendar.getInstance();
		fechNac = calendar.getTime();
		
		PersonaNaturalModel model = personaNaturalProvider.addPersonaNatural(pais, tipoDocumento, numDoc, apePaterno, apeMaterno, nombres, fechNac, sexo);
		System.out.println("Model created:" + model);
	}
		*/
}
