package org.softgreen.sistcoop.persona.models;

import java.math.BigDecimal;

import javax.ejb.Remote;

import org.softgreen.sistcoop.persona.providers.Provider;

@Remote
public interface AccionistaProvider extends Provider {

	AccionistaModel getAccionistaById(Long id);

	AccionistaModel addAccionista(PersonaJuridicaModel pjModel, PersonaNaturalModel pnModel, BigDecimal porcentaje);

	boolean removeAccionista(AccionistaModel accionistaModel);

}
