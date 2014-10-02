package org.softgreen.sistcoop.persona.client.models;

import java.util.List;

import javax.ejb.Remote;

import org.softgreen.sistcoop.persona.client.providers.Provider;

@Remote
public interface CountryProvider extends Provider {

	public CountryModel findByAlpha2Code(String code);

	public CountryModel findByAlpha3Code(String code);

	public CountryModel findByNumericCode(String code);

	public List<CountryModel> findAll();
}
