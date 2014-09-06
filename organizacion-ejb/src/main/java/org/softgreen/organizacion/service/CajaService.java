package org.softgreen.organizacion.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Remote;

import org.softgreen.organizacion.entity.Boveda;
import org.softgreen.organizacion.entity.Caja;
import org.softgreen.organizacion.entity.DetalleHistorial;
import org.softgreen.organizacion.entity.HistorialCaja;
import org.softgreen.organizacion.exception.RollbackFailureException;

@Remote
public interface CajaService extends AbstractService<Integer, Caja> {

	public Set<Boveda> getBovedas(Integer idCaja);

	public List<HistorialCaja> getHistorial(Integer idCaja, Date dateDesde, Date dateHasta);

	public Map<String, DetalleHistorial> getDetalle(Integer idCaja);

	public void desactivar(Integer idCaja) throws RollbackFailureException;

}
