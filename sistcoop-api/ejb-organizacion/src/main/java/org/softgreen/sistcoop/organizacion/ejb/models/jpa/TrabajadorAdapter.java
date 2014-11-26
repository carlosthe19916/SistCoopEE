package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.AgenciaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TrabajadorCajaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TrabajadorEntity;

public class TrabajadorAdapter implements TrabajadorModel {

	private static final long serialVersionUID = 1L;

	protected TrabajadorEntity trabajadorEntity;
	protected EntityManager em;

	public TrabajadorAdapter(EntityManager em, TrabajadorEntity trabajadorEntity) {
		this.em = em;
		this.trabajadorEntity = trabajadorEntity;
	}

	public TrabajadorEntity getTrabajadorEntity() {
		return trabajadorEntity;
	}

	@Override
	public void commit() {
		em.merge(trabajadorEntity);
	}

	@Override
	public Integer getId() {
		return trabajadorEntity.getId();
	}

	@Override
	public String getTipoDocumento() {
		return trabajadorEntity.getTipoDocumento();
	}

	@Override
	public void setTipoDocumento(String tipoDocumento) {
		trabajadorEntity.setTipoDocumento(tipoDocumento);
	}

	@Override
	public String getNumeroDocumento() {
		return trabajadorEntity.getNumeroDocumento();
	}

	@Override
	public void setNumeroDocumento(String numeroDocumento) {
		trabajadorEntity.setNumeroDocumento(numeroDocumento);
	}

	@Override
	public String getUsuario() {
		return trabajadorEntity.getUsuario();
	}

	@Override
	public void setUsuario(String usuario) {
		trabajadorEntity.setUsuario(usuario);
	}

	@Override
	public boolean getEstado() {
		return trabajadorEntity.isEstado();
	}

	@Override
	public void setEstado(boolean estado) {
		trabajadorEntity.setEstado(estado);
	}

	@Override
	public AgenciaModel getAgencia() {
		return new AgenciaAdapter(em, trabajadorEntity.getAgencia());
	}

	@Override
	public void setAgencia(AgenciaModel agenciaModel) {
		AgenciaEntity agenciaEntity = AgenciaAdapter.toSucursalEntity(agenciaModel, em);
		trabajadorEntity.setAgencia(agenciaEntity);
	}

	@Override
	public List<TrabajadorCajaModel> getTrabajadorCajas() {
		Set<TrabajadorCajaEntity> trabajadorCajas = trabajadorEntity.getTrabajadorCajas();
		List<TrabajadorCajaModel> result = new ArrayList<TrabajadorCajaModel>();
		for (TrabajadorCajaEntity trabajadorCajaEntity : trabajadorCajas) {
			result.add(new TrabajadorCajaAdapter(em, trabajadorCajaEntity));
		}
		return result;
	}

	public static TrabajadorEntity toTrabajadorEntity(TrabajadorModel model, EntityManager em) {
		if (model instanceof TrabajadorAdapter) {
			return ((TrabajadorAdapter) model).getTrabajadorEntity();
		}
		return em.getReference(TrabajadorEntity.class, model.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof TrabajadorModel))
			return false;

		TrabajadorModel that = (TrabajadorModel) o;
		return that.getId().equals(getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

}
