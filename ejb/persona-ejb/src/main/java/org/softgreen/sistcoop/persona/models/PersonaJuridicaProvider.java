package org.softgreen.sistcoop.persona.models;

import java.util.List;

import javax.ejb.Remote;

import org.softgreen.sistcoop.persona.providers.Provider;

@Remote
public interface PersonaJuridicaProvider extends Provider{
	
	PersonaJuridicaModel getPersonaJuridica();
	
	void updatePersonaJuridica(PersonaJuridicaModel personaJuridicaModel);
	
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
