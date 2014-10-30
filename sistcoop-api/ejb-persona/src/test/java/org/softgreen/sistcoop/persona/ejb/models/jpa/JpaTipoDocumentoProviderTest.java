package org.softgreen.sistcoop.persona.ejb.models.jpa;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RunAs;
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
import org.softgreen.sistcoop.persona.client.util.Roles;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.TipoDocumentoEntity;

@RunWith(Arquillian.class)
@DeclareRoles({ Roles.ADMIN, Roles.USER })
@RunAs(Roles.ADMIN)
@PermitAll
public class JpaTipoDocumentoProviderTest {

	private final static Logger log = Logger.getLogger(JpaTipoDocumentoProviderTest.class.getName());

	@Inject
	TipoDocumentoProvider tipoDocumentoProvider;

	@Inject
	RepresentationToModel representationToModel;

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addClasses(Resources.class,
		/** ModelToRepresentation **/
		ModelToRepresentation.class, RepresentationToModel.class,

		/** Extras **/
		PersonaNaturalModel.class, PersonaNaturalProvider.class, PersonaNaturalRepresentation.class, PersonaJuridicaModel.class, PersonaJuridicaProvider.class, PersonaJuridicaRepresentation.class,

		/** Enums **/
		TipoPersona.class,
		/** Models **/
		Model.class, TipoDocumentoModel.class,
		/** Providers **/
		TipoDocumentoProvider.class,
		/** Representation **/
		TipoDocumentoRepresentation.class,
		/** Adapters **/
		TipoDocumentoAdapter.class,
		/** Jpa providers **/
		Provider.class, JpaTipoDocumentoProvider.class,
		/** entities **/
		TipoDocumentoEntity.class)

		.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
		/** beans.xml **/
		.addAsWebInfResource(EmptyAsset.INSTANCE, "META-INF/beans.xml")
		/** deploy test DS **/
		.addAsWebInfResource("test-ds.xml");
	}

	@Test
	@InSequence(1)
	public void addTipoDocumentoPN() throws Exception {		
		String abreviatura = "DNI";
		String denominacion = "Documento nacional de identidad";
		int cantidadCaracteres = 8;
		TipoPersona tipoPersona = TipoPersona.NATURAL;
		TipoDocumentoModel model = tipoDocumentoProvider.addTipoDocumento(abreviatura, denominacion, cantidadCaracteres, tipoPersona);

		log.info(model.getAbreviatura() + " creado");
	}

	@Test
	@InSequence(2)
	public void addTipoDocumentoPJ() throws Exception {
		String abreviatura = "RUC";
		String denominacion = "Registro unico de contribuyente";
		int cantidadCaracteres = 11;
		TipoPersona tipoPersona = TipoPersona.JURIDICA;
		TipoDocumentoModel model = tipoDocumentoProvider.addTipoDocumento(abreviatura, denominacion, cantidadCaracteres, tipoPersona);

		log.info(model.getAbreviatura() + " creado");
	}

	@Test
	@InSequence(3)
	public void getTipoDocumentoByAbreviatura() throws Exception {
		TipoDocumentoModel model1 = tipoDocumentoProvider.getTipoDocumentoByAbreviatura("DNI");
		TipoDocumentoModel model2 = tipoDocumentoProvider.getTipoDocumentoByAbreviatura("RUC");
		TipoDocumentoModel model3 = tipoDocumentoProvider.getTipoDocumentoByAbreviatura("XXX");

		log.info(model1.getAbreviatura() + " encontrado");
		log.info(model2.getAbreviatura() + " encontrado");
		if (model3 == null) {
			log.info(" encontrado en nulo");
		} else {
			log.log(Level.SEVERE, "Encontrado en no nulo");
			throw new Exception("getTipoDocumentoByAbreviatura() error");
		}
	}

	@Test
	@InSequence(4)
	public void getTiposDocumento() throws Exception {
		List<TipoDocumentoModel> list = tipoDocumentoProvider.getTiposDocumento();
		log.info("List tiposDocumento, Size:" + list.size());
	}

	@Test
	@InSequence(5)
	public void getTiposDocumentoByTipoPersona() throws Exception {
		List<TipoDocumentoModel> listPN = tipoDocumentoProvider.getTiposDocumento(TipoPersona.NATURAL);
		List<TipoDocumentoModel> listPJ = tipoDocumentoProvider.getTiposDocumento(TipoPersona.JURIDICA);
		log.info("List tiposDocumento Natural, Size:" + listPN.size());
		log.info("List tiposDocumento Juridica, Size:" + listPJ.size());
	}

	@Test
	@InSequence(6)
	public void remove() throws Exception {
		TipoDocumentoModel model = tipoDocumentoProvider.getTipoDocumentoByAbreviatura("DNI");
		tipoDocumentoProvider.removeTipoDocumento(model);
		log.info("Tipo documento Removed");

		TipoDocumentoModel model2 = tipoDocumentoProvider.getTipoDocumentoByAbreviatura("DNI");
		if (model2 != null)
			throw new Exception("Tipo documento no fue eliminado");
	}

	@Test
	@InSequence(7)
	public void modelToRepresentation() {
		List<TipoDocumentoModel> list = tipoDocumentoProvider.getTiposDocumento();
		if (list.size() < 1)
			log.log(Level.WARNING, "ModelToRepresentation() size = 0");
		for (TipoDocumentoModel model : list) {
			TipoDocumentoRepresentation representation = ModelToRepresentation.toRepresentation(model);
			log.info("Representation:" + representation.getAbreviatura());
		}
	}

	@Test
	@InSequence(8)
	public void representationToModel() {
		TipoDocumentoRepresentation representation = new TipoDocumentoRepresentation();
		representation.setAbreviatura("PASS");
		representation.setDenominacion("Pasaporte");
		representation.setCantidadCaracteres(11);
		representation.setTipoPersona(TipoPersona.NATURAL.toString());

		TipoDocumentoModel model = representationToModel.createTipoDocumento(representation, tipoDocumentoProvider);
		log.info("representationToModel:" + model.getAbreviatura());
	}

}
