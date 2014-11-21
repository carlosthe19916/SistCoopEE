package org.softgreen.sistcoop.organizacion.restapi.config;

import java.io.Serializable;

public class JsendWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Jsend data;

	public JsendWrapper() {
		// TODO Auto-generated constructor stub
	}

	public Jsend getData() {
		return data;
	}

	public void setData(Jsend data) {
		this.data = data;
	}

}
