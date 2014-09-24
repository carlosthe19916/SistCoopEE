package org.softgreen.organizacion.controller;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;

import org.softgreen.organizacion.entity.PendienteCaja;
import org.softgreen.organizacion.entity.dto.TransaccionBovedaCajaView;
import org.softgreen.organizacion.entity.dto.TransaccionCajaCajaView;
import org.softgreen.organizacion.service.TransaccionInternaService;

@Named
@Stateless
@Remote(TransaccionInternaService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TransaccionInternaServiceBean implements TransaccionInternaService {

	@Override
	public List<TransaccionBovedaCajaView> getTransaccionesEnviadasBovedaCaja(Long idCaja, Long idHistorial) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransaccionBovedaCajaView> getTransaccionesRecibidasBovedaCaja(Long idCaja, Long idHistorial) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransaccionCajaCajaView> getTransaccionesEnviadasCajaCaja(Long idCaja, Long idHistorial) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransaccionCajaCajaView> getTransaccionesRecibidasCajaCaja(Long idCaja, Long idHistorial) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PendienteCaja> getPendientes(Long idCaja, Long idHistorial) {
		// TODO Auto-generated method stub
		return null;
	}

}
