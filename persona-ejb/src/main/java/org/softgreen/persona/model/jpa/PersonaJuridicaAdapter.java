package org.softgreen.persona.model.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.softgreen.persona.model.AccionistaModel;
import org.softgreen.persona.model.PersonaJuridicaModel;
import org.softgreen.persona.model.PersonaNaturalModel;
import org.softgreen.persona.model.TipoDocumentoModel;
import org.softgreen.persona.model.jpa.entity.PersonaJuridicaEntity;
import org.softgreen.persona.model.jpa.entity.PersonaNaturalEntity;
import org.softgreen.persona.model.type.TipoEmpresa;

public class PersonaJuridicaAdapter implements PersonaJuridicaModel {

	protected PersonaJuridicaEntity personaJuridicaEntity;
	protected EntityManager em;

	public PersonaJuridicaAdapter(EntityManager em,
			PersonaJuridicaEntity personaJuridicaEntity) {
		this.em = em;
		this.personaJuridicaEntity = personaJuridicaEntity;
	}
	
	public static PersonaJuridicaEntity toPersonaJuridicaEntity(PersonaJuridicaModel personaJuridicaModel, EntityManager em){
		return null;
	}

	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof PersonaJuridicaModel))
			return false;

		PersonaJuridicaModel that = (PersonaJuridicaModel) o;
		return that.getId().equals(getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}


	@Override
	public void commit() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PersonaNaturalModel getRepresentanteLegal() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PersonaNaturalModel setRepresentanteLegal(
			PersonaNaturalModel representanteLegal) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public AccionistaModel addAccionista(AccionistaModel accionistaModel) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void updateAccionista(AccionistaModel accionistaModel) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean removeAccionista(AccionistaModel accionistaModel) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<AccionistaModel> getAccionistas() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getCodigoPais() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setCodigoPais(String codigoPais) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public TipoDocumentoModel getTipoDocumento() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setTipoDocumento(TipoDocumentoModel tipoDocumento) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getNumeroDocumento() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setNumeroDocumento(String numeroDocumento) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getRazonSocial() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setRazonSocial(String razonSocial) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getNombreComercial() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setNombreComercial(String nombreComercial) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Date getFechaConstitucion() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setFechaConstitucion(Date fechaConstitucion) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getActividadPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setActividadPrincipal(String actividadPrincipal) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public TipoEmpresa getTipoEmpresa() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean isFinLucro() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void setFinLucro(boolean finLucro) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getUbigeo() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setUbigeo(String ubigeo) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getDireccion() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setDireccion(String direccion) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getReferencia() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setReferencia(String referencia) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getTelefono() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setTelefono(String telefono) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getCelular() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setCelular(String celular) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setEmail(String email) {
		// TODO Auto-generated method stub
		
	}
}
