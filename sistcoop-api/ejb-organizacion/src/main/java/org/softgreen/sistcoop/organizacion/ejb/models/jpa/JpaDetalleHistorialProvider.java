package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.math.BigDecimal;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialProvider;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;

@Named
@Stateless
@Local(DetalleHistorialProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaDetalleHistorialProvider implements DetalleHistorialProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public DetalleHistorialModel addDetalleHistorial(HistorialModel historialModel, int cantidad, BigDecimal valor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DetalleHistorialModel getDetalleHistorialById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
