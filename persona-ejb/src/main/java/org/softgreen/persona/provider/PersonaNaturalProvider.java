package org.softgreen.persona.provider;

import java.util.List;

import javax.ejb.Remote;

import org.softgreen.persona.model.PersonaNaturalModel;
import org.softgreen.persona.model.TipoDocumentoModel;

@Remote
public interface PersonaNaturalProvider extends Provider{

	PersonaNaturalModel addPersonaNatural(
			PersonaNaturalModel personaNaturalModel);

	boolean removePersonaNatural(PersonaNaturalModel personaNaturalModel);

	PersonaNaturalModel getPersonaNaturalById(Long id);

	PersonaNaturalModel getPersonaNaturalByTipoNumeroDoc(
			TipoDocumentoModel tipoDocumento, String numeroDocumento);

	List<PersonaNaturalModel> getPersonasNaturales();

	int getPersonasNaturalesCount();

	List<PersonaNaturalModel> getPersonasNaturales(int firstResult,
			int maxResults);

	List<PersonaNaturalModel> searchForNumeroDocumento(String numeroDocumento);

	List<PersonaNaturalModel> searchForNumeroDocumento(String numeroDocumento,
			int firstResult, int maxResults);

	List<PersonaNaturalModel> searchForFilterText(String filterText);

	List<PersonaNaturalModel> searchForFilterText(String filterText,
			int firstResult, int maxResults);

}
