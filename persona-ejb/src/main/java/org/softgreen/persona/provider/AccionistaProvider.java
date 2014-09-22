package org.softgreen.persona.provider;

import javax.ejb.Remote;

import org.softgreen.persona.model.AccionistaModel;

@Remote
public interface AccionistaProvider extends Provider {

	AccionistaModel getAccionistaById(Long id);

	AccionistaModel addAccionista(AccionistaModel accionistaModel);

	boolean removeAccionista(AccionistaModel accionistaModel);

}
