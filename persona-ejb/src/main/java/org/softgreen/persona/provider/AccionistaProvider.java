package org.softgreen.persona.provider;

import java.math.BigDecimal;

import javax.ejb.Remote;

import org.softgreen.persona.model.AccionistaModel;
import org.softgreen.persona.model.PersonaJuridicaModel;
import org.softgreen.persona.model.PersonaNaturalModel;

@Remote
public interface AccionistaProvider extends Provider {

	AccionistaModel getAccionistaById(Long id);

	AccionistaModel addAccionista(PersonaJuridicaModel pjModel, PersonaNaturalModel pnModel, BigDecimal porcentaje);

	boolean removeAccionista(AccionistaModel accionistaModel);

}
