package org.softgreen.sistcoop.persona.client.models;

public interface DistritoModel extends Model {

	public Integer getId();

	public String getCodigo();

	public void setCodigo(String codigo);

	public String getDenominacion();

	public void setDenominacion(String denominacion);

	public ProvinciaModel getProvincia();

	public void setProvincia(ProvinciaModel provinciaModel);

}