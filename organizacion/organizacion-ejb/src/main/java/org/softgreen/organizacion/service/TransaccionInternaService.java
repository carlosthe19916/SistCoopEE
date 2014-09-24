package org.softgreen.organizacion.service;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import org.softgreen.organizacion.entity.PendienteCaja;
import org.softgreen.organizacion.entity.dto.TransaccionBovedaCajaView;
import org.softgreen.organizacion.entity.dto.TransaccionCajaCajaView;

@Remote
public interface TransaccionInternaService {

	public List<TransaccionBovedaCajaView> getTransaccionesEnviadasBovedaCaja(Long idCaja, Long idHistorial);

	public List<TransaccionBovedaCajaView> getTransaccionesRecibidasBovedaCaja(Long idCaja, Long idHistorial);

	public List<TransaccionCajaCajaView> getTransaccionesEnviadasCajaCaja(Long idCaja, Long idHistorial);

	public List<TransaccionCajaCajaView> getTransaccionesRecibidasCajaCaja(Long idCaja, Long idHistorial);

	public Set<PendienteCaja> getPendientes(Long idCaja, Long idHistorial);
}
