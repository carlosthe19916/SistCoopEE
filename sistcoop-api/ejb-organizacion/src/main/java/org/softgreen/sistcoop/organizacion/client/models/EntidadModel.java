package org.softgreen.sistcoop.organizacion.client.models;

public interface EntidadModel extends Model {

	public Integer getId();

	public String getDenominacion();

	public void setDenominacion(String id);

	public String getAbreviatura();

	public void setAbreviatura(String id);

	public boolean getEstado();

	public void setEstado(boolean id);

}
