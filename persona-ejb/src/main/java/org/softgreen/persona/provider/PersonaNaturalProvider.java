package org.softgreen.persona.provider;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import org.softgreen.persona.model.PersonaNaturalModel;
import org.softgreen.persona.model.TipoDocumentoModel;
import org.softgreen.persona.model.type.Sexo;

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
