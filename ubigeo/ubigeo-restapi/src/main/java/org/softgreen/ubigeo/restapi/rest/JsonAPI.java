package org.softgreen.ubigeo.restapi.rest;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class JsonAPI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String api_version = "1.0";
	private Object data;

	@SuppressWarnings("rawtypes")
	private List messages;

	private Object meta;

	@SuppressWarnings("rawtypes")
	private List links;

	@SuppressWarnings("rawtypes")
	private List linked;

	public JsonAPI() {
		// TODO Auto-generated constructor stub
	}

	public static JsonAPI getSuccess(Object object) {
		JsonAPI jsonAPI = new JsonAPI();
		jsonAPI.setData(object);
		return jsonAPI;
	}

	@XmlElement
	public String getApi_version() {
		return api_version;
	}

	public void setApi_version(String api_version) {
		this.api_version = api_version;
	}

	@XmlElement
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@SuppressWarnings("rawtypes")
	@XmlElement
	public List getMessages() {
		return messages;
	}

	@SuppressWarnings("rawtypes")
	public void setMessages(List messages) {
		this.messages = messages;
	}

	@XmlElement
	public Object getMeta() {
		return meta;
	}

	public void setMeta(Object meta) {
		this.meta = meta;
	}

	@SuppressWarnings("rawtypes")
	@XmlElement
	public List getLinks() {
		return links;
	}

	@SuppressWarnings("rawtypes")
	public void setLinks(List links) {
		this.links = links;
	}

	@SuppressWarnings("rawtypes")
	@XmlElement
	public List getLinked() {
		return linked;
	}

	@SuppressWarnings("rawtypes")
	public void setLinked(List linked) {
		this.linked = linked;
	}

}
