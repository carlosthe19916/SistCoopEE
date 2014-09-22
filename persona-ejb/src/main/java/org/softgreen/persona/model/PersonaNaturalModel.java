package org.softgreen.persona.model;

import java.util.Date;

import org.softgreen.persona.model.jpa.entity.TipoDocumentoEntity;
import org.softgreen.persona.model.type.EstadoCivil;
import org.softgreen.persona.model.type.Sexo;

public interface PersonaNaturalModel extends Model{

	Long getId();

	String getCodigoPais();

	void setCodigoPais(String codigoPais);

	TipoDocumentoEntity getTipoDocumento();

	void setTipoDocumento(TipoDocumentoEntity tipoDocumento);

	String getNumeroDocumento();

	void setNumeroDocumento(String numeroDocumento);

	String getApellidoPaterno();

	void setApellidoPaterno(String apellidoPaterno);

	String getApellidoMaterno();

	void setApellidoMaterno(String apellidoMaterno);

	String getNombres();

	void setNombres(String nombres);

	Date getFechaNacimiento();

	void setFechaNacimiento(Date fechaNacimiento);

	Sexo getSexo();

	void setSexo(Sexo sexo);

	EstadoCivil getEstadoCivil();

	void setEstadoCivil(EstadoCivil estadoCivil);

	String getOcupacion();

	void setOcupacion(String ocupacion);

	String getUrlFoto();

	void setUrlFoto(String urlFoto);

	String getUrlFirma();

	void setUrlFirma(String urlFirma);

	String getUbigeo();

	void setUbigeo(String ubigeo);

	String getDireccion();

	void setDireccion(String direccion);

	String getReferencia();

	void setReferencia(String referencia);

	String getTelefono();

	void setTelefono(String telefono);

	String getCelular();

	void setCelular(String celular);

	String getEmail();

	void setEmail(String email);

}