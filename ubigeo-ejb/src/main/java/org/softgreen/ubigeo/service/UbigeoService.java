package org.softgreen.ubigeo.service;

import java.util.List;

import javax.ejb.Remote;

import org.softgreen.ubigeo.entity.Departamento;
import org.softgreen.ubigeo.entity.Distrito;
import org.softgreen.ubigeo.entity.Provincia;

@Remote
public interface UbigeoService {

	public List<Departamento> getDepartamentos();

	public List<Provincia> getProvincias(String codigoDepartamento);

	public List<Distrito> getDistritos(String codigoDepartamento, String codigoProvincia);

}
