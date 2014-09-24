package org.softgreen.ubigeo.service;

import javax.ejb.Remote;

import org.softgreen.ubigeo.entity.Country;

@Remote
public interface CountryService extends AbstractService<Integer, Country> {

	public Country findByAlpha2Code(String code);

	public Country findByAlpha3Code(String code);

	public Country findByNumericCode(String code);

}
