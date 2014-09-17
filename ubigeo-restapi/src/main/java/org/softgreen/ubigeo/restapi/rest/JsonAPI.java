package org.softgreen.ubigeo.restapi.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class JsonAPI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List data = new ArrayList();
	private List messages;

	private Object meta;
	private List links = new ArrayList();
	private List linked = new ArrayList();

	public JsonAPI() {
		// TODO Auto-generated constructor stub
	}

	class SingleResponse {
		
	}
}
