package org.softgreen.socio.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import org.softgreen.persona.entity.Persona;
import org.softgreen.socio.entity.CuentaAporte;
import org.softgreen.socio.entity.Socio;

@Remote
public interface SocioService extends AbstractService<Long, Socio> {

	public List<Socio> findAllView(Boolean estadoCuentaAporte, Boolean estadoSocio, Integer offset, Integer limit);

	public List<Socio> findAllView(String filterText, Boolean estadoCuentaAporte, Boolean estadoSocio, Integer offset, Integer limit);

	public Persona getApoderado(BigInteger idSocio);

	public CuentaAporte getCuentaAporte(BigInteger idSocio);

	public Set<Persona> getBeneficiarios(BigInteger idSocio);

	public Persona findBeneficiarioById(BigInteger id);

}
