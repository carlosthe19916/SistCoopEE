package org.softgreen.sistcoop.persona.ejb.models.jpa;

import java.util.Calendar;
import java.util.Date;
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
import org.softgreen.sistcoop.persona.clien.enums.EstadoCivil;
import org.softgreen.sistcoop.persona.clien.enums.Sexo;
import org.softgreen.sistcoop.persona.clien.enums.TipoPersona;
import org.softgreen.sistcoop.persona.client.models.Model;
import org.softgreen.sistcoop.persona.client.models.PersonaJuridicaModel;
import org.softgreen.sistcoop.persona.client.models.PersonaJuridicaProvider;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalProvider;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.persona.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.persona.client.providers.Provider;
import org.softgreen.sistcoop.persona.client.representations.idm.PersonaJuridicaRepresentation;
import org.softgreen.sistcoop.persona.client.representations.idm.PersonaNaturalRepresentation;
import org.softgreen.sistcoop.persona.client.representations.idm.TipoDocumentoRepresentation;
import org.softgreen.sistcoop.persona.client.util.Resources;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.PersonaEntity;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.PersonaNaturalEntity;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.TipoDocumentoEntity;

@RunWith(Arquillian.class)
public class JpaPersonaNaturalProviderTest {

	private final static Logger log = Logger.getLogger(JpaPersonaNaturalProviderTest.class.getName());

	@Inject
	TipoDocumentoProvider tipoDocumentoProvider;
	
	@Inject
	PersonaNaturalProvider personaNaturalProvider;

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addClasses(Resources.class,
		/** ModelToRepresentation **/
		ModelToRepresentation.class, RepresentationToModel.class,
		
		/**Extras**/
		TipoDocumentoModel.class, TipoDocumentoProvider.class, TipoDocumentoRepresentation.class,
		PersonaJuridicaModel.class, PersonaJuridicaProvider.class, PersonaJuridicaRepresentation.class,
		
		/** Enums **/
		Sexo.class, EstadoCivil.class,
		/** Models **/
		Model.class, PersonaNaturalModel.class,
		/** Providers **/
		PersonaNaturalProvider.class,
		/** Representation **/
		PersonaNaturalRepresentation.class,
		/** Adapters **/
		PersonaNaturalAdapter.class,
		/** Jpa providers **/
		Provider.class, JpaPersonaNaturalProvider.class,
		/** entities **/
		TipoDocumentoEntity.class, PersonaEntity.class, PersonaNaturalEntity.class)

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
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura("DNI");
		String numeroDocumento = "46779354";
		String apellidoPaterno = "Feria";
		String apellidoMaterno = "Vila";
		String nombres = "Carlos";
		Date fechaNacimiento = Calendar.getInstance().getTime();
		Sexo sexo = Sexo.MASCULINO;
		
		PersonaNaturalModel model = personaNaturalProvider.addPersonaNatural(codigoPais, tipoDocumentoModel, numeroDocumento, apellidoPaterno, apellidoMaterno, nombres, fechaNacimiento, sexo);

		log.info(model.getId() + " creado");
	}

	

}
