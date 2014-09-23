package org.softgreen.persona.provider;

import java.util.List;

import javax.ejb.Remote;

import org.softgreen.persona.model.PersonaJuridicaModel;
import org.softgreen.persona.model.TipoDocumentoModel;

@Remote
public interface PersonaJuridicaProvider extends Provider{

	PersonaJuridicaModel addPersonaJuridica(TipoDocumentoModel tipoDocumentoModel, String numeroDocumento, String razonSocial);
	
	boolean removePersonaJuridica(PersonaJuridicaModel personaJuridicaModel);

	PersonaJuridicaModel getPersonaJuridicaById(Long id);

	PersonaJuridicaModel getPersonaJuridicaByTipoNumeroDoc(TipoDocumentoModel tipoDocumento, String numeroDocumento);

	List<PersonaJuridicaModel> getPersonasJuridicas();

	int getPersonasJuridicasCount();

	List<PersonaJuridicaModel> getPersonasJuridicas(int firstResult,int maxResults);

	List<PersonaJuridicaModel> searchForNumeroDocumento(String numeroDocumento);

	List<PersonaJuridicaModel> searchForNumeroDocumento(String numeroDocumento,int firstResult, int maxResults);

	List<PersonaJuridicaModel> searchForFilterText(String filterText);

	List<PersonaJuridicaModel> searchForFilterText(String filterText,int firstResult, int maxResults);
}
