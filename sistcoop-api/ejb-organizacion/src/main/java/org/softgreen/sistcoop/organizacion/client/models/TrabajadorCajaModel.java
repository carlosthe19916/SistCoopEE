package org.softgreen.sistcoop.organizacion.client.models;


public interface TrabajadorCajaModel extends Model {

	Integer getId();

	TrabajadorModel getTrabajador();

	CajaModel getCaja();

	void setCaja(CajaModel cajaModel);

}
