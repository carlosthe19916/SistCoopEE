package org.softgreen.persona.restapi.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class JsonAPI implements Serializable {

	private List data = new ArrayList();

	public JsonAPI() {
		// TODO Auto-generated constructor stub
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	

}
