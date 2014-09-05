package org.softgreen.organizacion.service.nt;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import org.softgreen.organizacion.entity.Boveda;
import org.softgreen.organizacion.entity.Caja;
import org.softgreen.organizacion.entity.DetalleHistorial;
import org.softgreen.organizacion.entity.HistorialCaja;
import org.softgreen.organizacion.entity.PendienteCaja;
import org.softgreen.organizacion.entity.TransaccionBovedaCaja;
import org.softgreen.organizacion.entity.TransaccionCajaCaja;
import org.softgreen.organizacion.entity.dto.ResumenOperacionesCaja;
import org.softgreen.organizacion.entity.dto.VoucherCompraVenta;
import org.softgreen.organizacion.entity.dto.VoucherTransaccionBancaria;
import org.softgreen.organizacion.entity.dto.VoucherTransaccionCuentaAporte;
import org.softgreen.organizacion.entity.dto.VoucherTransferenciaBancaria;

@Remote
public interface CajaServiceNT extends AbstractServiceNT<Integer, Caja> {

	public Set<Boveda> getBovedas(Integer idCaja);

	public Set<HistorialCaja> getHistorialCaja(Integer idCaja, Date dateDesde, Date dateHasta);

	public Set<DetalleHistorial> getDetalleCaja(Integer idCaja, Long idHistorialCaja);

	public List<TransaccionBovedaCaja> getTransaccionesEnviadasBovedaCaja(Integer idCaja, Long idHistorial);

	public List<TransaccionBovedaCaja> getTransaccionesRecibidasBovedaCaja(Integer idCaja, Long idHistorial);

	public Set<TransaccionCajaCaja> getTransaccionesEnviadasCajaCaja(BigInteger idCaja, BigInteger idHistorialCaja);

	public Set<TransaccionCajaCaja> getTransaccionesRecibidasCajaCaja(BigInteger idCaja, BigInteger idHistorialCaja);

	public ResumenOperacionesCaja getResumenOperacionesCaja(BigInteger idHistorial);

	public VoucherTransaccionCuentaAporte getVoucherCuentaAporte(BigInteger idTransaccion);

	public VoucherTransaccionBancaria getVoucherTransaccionBancaria(BigInteger idTransaccionBancaria);

	public VoucherTransferenciaBancaria getVoucherTransferenciaBancaria(BigInteger idTransferencia);

	public VoucherCompraVenta getVoucherCompraVenta(BigInteger idTransaccionCompraVenta);

	public Set<PendienteCaja> getPendientes(BigInteger idCaja, BigInteger idHistorialCaja);

}
