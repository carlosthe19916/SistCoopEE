package org.softgreen.sistcoop.persona.client.models;

import java.util.List;

import javax.ejb.Remote;

import org.softgreen.sistcoop.persona.clien.enums.TipoPersona;
import org.softgreen.sistcoop.persona.client.providers.Provider;

@Remote
public interface TipoDocumentoProvider extends Provider {

	TipoDocumentoModel addTipoDocumento(String abreviatura, String denominacion, int cantidadCaracteres, TipoPersona tipoPersona);

	boolean removeTipoDocumento(TipoDocumentoModel tipoDocumentoModel);

	TipoDocumentoModel getTipoDocumentoByAbreviatura(String abreviatura);

	List<TipoDocumentoModel> getTiposDocumento();

	List<TipoDocumentoModel> getTiposDocumento(TipoPersona tipoPersona);

}
