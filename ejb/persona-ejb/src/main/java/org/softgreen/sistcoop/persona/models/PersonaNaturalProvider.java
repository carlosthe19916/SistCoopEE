package org.softgreen.sistcoop.persona.models;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import org.softgreen.sistcoop.persona.enums.Sexo;
import org.softgreen.sistcoop.persona.providers.Provider;

@Remote
public interface PersonaNaturalProvider extends Provider {

	PersonaNaturalModel addPersonaNatural(TipoDocumentoModel tipoDocumento, String numeroDoc, String paterno, String materno, String nombres, Sexo sexo, Date fechaNac);
	
	boolean removePersonaNatural(PersonaNaturalModel personaNaturalModel);

	PersonaNaturalModel getPersonaNaturalById(Long id);

	PersonaNaturalModel getPersonaNaturalByTipoNumeroDoc(TipoDocumentoModel tipoDocumento, String numeroDocumento);

	List<PersonaNaturalModel> getPersonasNaturales();

	int getPersonasNaturalesCount();

	List<PersonaNaturalModel> getPersonasNaturales(int firstResult,int maxResults);

	List<PersonaNaturalModel> searchForNumeroDocumento(String numeroDocumento);

	List<PersonaNaturalModel> searchForNumeroDocumento(String numeroDocumento, int firstResult, int maxResults);

	List<PersonaNaturalModel> searchForFilterText(String filterText);

	List<PersonaNaturalModel> searchForFilterText(String filterText,int firstResult, int maxResults);

}
