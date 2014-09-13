package org.softgreen.persona.service;

import java.util.List;

import javax.ejb.Remote;

import org.softgreen.persona.entity.TipoDocumento;
import org.softgreen.persona.entity.type.TipoPersona;

@Remote
public interface MaestroService {

	public List<TipoDocumento> getTipoDocumento(TipoPersona tipoPersona);

}
