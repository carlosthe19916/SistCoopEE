package org.softgreen.sistcoop.ubigeo.client.models;

import java.util.List;

import javax.ejb.Local;

import org.softgreen.sistcoop.ubigeo.client.providers.Provider;

@Local
public interface CountryProvider extends Provider {

	public CountryModel findByAlpha2Code(String code);

	public CountryModel findByAlpha3Code(String code);

	public CountryModel findByNumericCode(String code);

	public List<CountryModel> findAll();
	
}
