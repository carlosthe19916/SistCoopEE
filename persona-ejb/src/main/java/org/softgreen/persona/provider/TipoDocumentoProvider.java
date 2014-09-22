package org.softgreen.persona.provider;

import java.util.List;

import javax.ejb.Remote;

import org.softgreen.persona.model.TipoDocumentoModel;

@Remote
public interface TipoDocumentoProvider extends Provider{

	TipoDocumentoModel getTipoDocumentoByAbreviatura(String abreviatura);
	
	List<TipoDocumentoModel> getTiposDocumento();
	
}
