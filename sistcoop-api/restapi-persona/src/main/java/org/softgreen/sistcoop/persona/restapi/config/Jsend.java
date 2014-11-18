package org.softgreen.sistcoop.persona.restapi.config;

import java.io.Serializable;

public class Jsend implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private Object id;

	public static JsendWrapper getSuccessJSend(Object id) {
		Jsend jsend = new Jsend();
		jsend.setId(id);
		
		JsendWrapper wrapper = new JsendWrapper();
		wrapper.setData(jsend);
		return wrapper;
	}

	private Jsend() {
		// TODO Auto-generated constructor stub
	}	

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

}
