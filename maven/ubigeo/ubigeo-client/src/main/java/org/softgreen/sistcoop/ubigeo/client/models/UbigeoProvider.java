package org.softgreen.sistcoop.ubigeo.client.models;

import java.util.List;

import javax.ejb.Remote;

import org.softgreen.sistcoop.ubigeo.client.providers.Provider;

@Remote
public interface UbigeoProvider extends Provider {

	public List<DepartamentoModel> getDepartamentos();

	public List<ProvinciaModel> getProvincias(String codigoDepartamento);

	public List<DistritoModel> getDistritos(String codigoDepartamento, String codigoProvincia);


}
