package org.softgreen.sistcoop.ubigeo.client.models;

import java.util.Set;

public interface DepartamentoModel extends Model {

	Integer getId();

	String getCodigo();

	void setCodigo(String codigo);

	String getDenominacion();

	void setDenominacion(String denominacion);

	Set<ProvinciaModel> getProvincias();

	void setProvincias(Set<ProvinciaModel> provincias);

}