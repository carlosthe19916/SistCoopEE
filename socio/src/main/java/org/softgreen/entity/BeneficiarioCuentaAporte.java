package org.softgreen.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("cuentaAporte")
public class BeneficiarioCuentaAporte extends Beneficiario {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CuentaAporte cuentaAporte;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public CuentaAporte getCuentaAporte() {
		return cuentaAporte;
	}

	public void setCuentaAporte(CuentaAporte cuentaAporte) {
		this.cuentaAporte = cuentaAporte;
	}
}
