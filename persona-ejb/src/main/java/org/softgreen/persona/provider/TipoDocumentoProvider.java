package org.softgreen.persona.provider;

import java.util.List;

import javax.ejb.Remote;

import org.softgreen.persona.model.TipoDocumentoModel;
import org.softgreen.persona.model.type.TipoPersona;

@Remote
public interface TipoDocumentoProvider extends Provider {

	TipoDocumentoModel getTipoDocumentoByAbreviatura(String abreviatura);

	List<TipoDocumentoModel> getTiposDocumento();

	List<TipoDocumentoModel> getTiposDocumento(TipoPersona tipoPersona);

	TipoDocumentoModel addTipoDocumento(String abreviatura, String denominacion, int maxLength, TipoPersona tipoPersona);

	boolean removeTipoDocumento(TipoDocumentoModel tipoDocumentoModel);

}
