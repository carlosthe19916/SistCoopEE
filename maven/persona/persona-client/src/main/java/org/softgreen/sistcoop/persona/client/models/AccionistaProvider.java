package org.softgreen.sistcoop.persona.client.models;

import java.math.BigDecimal;

import org.softgreen.sistcoop.persona.client.providers.Provider;

public interface AccionistaProvider extends Provider{

	AccionistaModel addAccionista(PersonaJuridicaModel pjModel, PersonaNaturalModel pnModel, BigDecimal porcentaje);

	boolean removeAccionista(AccionistaModel accionistaModel);

	AccionistaModel getAccionistaById(Long id);

}
