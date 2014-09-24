package org.softgreen.organizacion.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.joda.time.LocalDate;
import org.softgreen.exception.NonexistentEntityException;
import org.softgreen.exception.PreexistingEntityException;
import org.softgreen.exception.RollbackFailureException;
import org.softgreen.organizacion.dao.DAO;
import org.softgreen.organizacion.dao.QueryParameter;
import org.softgreen.organizacion.entity.Boveda;
import org.softgreen.organizacion.entity.BovedaCaja;
import org.softgreen.organizacion.entity.Caja;
import org.softgreen.organizacion.entity.DetalleHistorial;
import org.softgreen.organizacion.entity.HistorialCaja;
import org.softgreen.organizacion.service.CajaService;

@Named
@Stateless
@Remote(CajaService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CajaServiceBean implements CajaService {

	@Inject
	private DAO<Integer, Caja> cajaDAO;

	@Inject
	private DAO<Long, HistorialCaja> historialCajaDAO;

	@Inject
	private Validator validator;

	@Override
	public Caja findById(Integer id) {
		return cajaDAO.find(id);
	}

	@Override
	public List<Caja> findAll() {
		return cajaDAO.findAll();
	}

	@Override
	public int count() {
		return cajaDAO.count();
	}

	@Override
	public Integer create(Caja t) throws PreexistingEntityException, RollbackFailureException {
		Set<ConstraintViolation<Caja>> violations = validator.validate(t);
		if (violations.isEmpty()) {
			cajaDAO.create(t);
			return t.getId();
		} else {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
	}

	@Override
	public void update(Integer id, Caja t) throws NonexistentEntityException, PreexistingEntityException, RollbackFailureException {
		Caja agencia = cajaDAO.find(id);
		if (agencia != null) {
			Set<ConstraintViolation<Caja>> violations = validator.validate(t);
			if (violations.isEmpty()) {
				t.setId(id);
				cajaDAO.update(agencia);
			} else {
				throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
			}
		} else {
			throw new NonexistentEntityException("Agencia no existente, UPDATE no ejecutado");
		}
	}

	@Override
	public void delete(Integer id) throws NonexistentEntityException, RollbackFailureException {
		Caja agencia = cajaDAO.find(id);
		if (agencia != null) {
			cajaDAO.delete(agencia);
		} else {
			throw new NonexistentEntityException("Agencia no existente, DELETE no ejecutado");
		}
	}

	@Override
	public Set<Boveda> getBovedas(Integer idCaja) {
		Caja caja = cajaDAO.find(idCaja);
		if (caja != null) {
			Set<BovedaCaja> bovedaCajas = caja.getBovedaCajas();
			Set<Boveda> result = new HashSet<Boveda>();
			for (BovedaCaja bc : bovedaCajas) {
				Boveda boveda = bc.getBoveda();
				result.add(boveda);
			}
			return result;
		} else {
			return null;
		}
	}

	@Override
	public List<HistorialCaja> getHistorial(Integer idCaja, Date dateDesde, Date dateHasta) {
		Caja caja = cajaDAO.find(idCaja);
		if (caja != null) {
			LocalDate localDesde = new LocalDate(dateDesde);
			LocalDate localHasta = new LocalDate(dateHasta);

			Date dateDesdeQuery = localDesde.toDateTimeAtStartOfDay().toDate();
			Date dateHastaQuery = localHasta.toDateTimeAtStartOfDay().toDate();

			QueryParameter queryParameter = QueryParameter.with("idCaja", caja.getId()).and("desde", dateDesdeQuery).and("hasta", dateHastaQuery);
			List<HistorialCaja> list = historialCajaDAO.findByNamedQuery(HistorialCaja.findByHistorialDateRange, queryParameter.parameters());
			return list;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, DetalleHistorial> getDetalle(Integer idCaja) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void desactivar(Integer idCaja) throws org.softgreen.organizacion.exception.RollbackFailureException {
		// TODO Auto-generated method stub

	}

}
