package org.softgreen.sistcoop.persona.models;

import java.util.List;

import javax.ejb.Remote;

import org.softgreen.sistcoop.persona.enums.TipoPersona;
import org.softgreen.sistcoop.persona.providers.Provider;

@Remote
public interface TipoDocumentoProvider extends Provider {

	TipoDocumentoModel getTipoDocumentoByAbreviatura(String abreviatura);

	List<TipoDocumentoModel> getTiposDocumento();

	List<TipoDocumentoModel> getTiposDocumento(TipoPersona tipoPersona);

	TipoDocumentoModel addTipoDocumento(String abreviatura, String denominacion, int maxLength, TipoPersona tipoPersona);

	boolean removeTipoDocumento(TipoDocumentoModel tipoDocumentoModel);

}
