package org.softgreen.organizacion.service.nt;

import javax.ejb.Remote;

import org.softgreen.organizacion.entity.PendienteCaja;
import org.softgreen.organizacion.entity.dto.VoucherPendienteCaja;

@Remote
public interface PendienteServiceNT extends AbstractServiceNT<Long, PendienteCaja> {

	public VoucherPendienteCaja getVoucherPendienteCaja(Long idPendienteCaja);

}
