package org.softgreen.sistcoop.persona.client.models;

import java.util.Set;

public interface ProvinciaModel extends Model {

	public Integer getId();

	public String getCodigo();

	public void setCodigo(String codigo);

	public String getDenominacion();

	public void setDenominacion(String denominacion);

	public DepartamentoModel getDepartamento();

	public void setDepartamento(DepartamentoModel departamentoModel);

	public Set<DistritoModel> getDistritos();

	public void setDistritos(Set<DistritoModel> provincias);

}