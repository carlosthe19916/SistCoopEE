package org.softgreen.sistcoop.ubigeo.client.models;

import java.util.List;

import javax.ejb.Local;

import org.softgreen.sistcoop.ubigeo.client.providers.Provider;

@Local
public interface CurrencyProvider extends Provider {

	public CurrencyModel findByCode(String code);

	public CurrencyModel findBySimbol(String simbol);

	public List<CurrencyModel> findAll();

}
