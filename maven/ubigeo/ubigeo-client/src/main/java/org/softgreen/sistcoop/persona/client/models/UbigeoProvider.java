package org.softgreen.sistcoop.persona.client.models;

import java.util.List;

import javax.ejb.Remote;

import org.softgreen.sistcoop.persona.client.providers.Provider;

@Remote
public interface UbigeoProvider extends Provider {

	public List<DepartamentoModel> getDepartamentos();

	public List<ProvinciaModel> getProvincias(String codigoDepartamento);

	public List<DistritoModel> getDistritos(String codigoDepartamento, String codigoProvincia);


}
