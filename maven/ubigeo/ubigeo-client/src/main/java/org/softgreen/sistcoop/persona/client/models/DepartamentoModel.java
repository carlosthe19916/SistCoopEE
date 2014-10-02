package org.softgreen.sistcoop.persona.client.models;

import java.util.Set;

public interface DepartamentoModel extends Model {

	public Integer getId();

	public String getCodigo();

	public void setCodigo(String codigo);

	public String getDenominacion();

	public void setDenominacion(String denominacion);

	public Set<ProvinciaModel> getProvincias();

	public void setProvincias(Set<ProvinciaModel> provincias);

}