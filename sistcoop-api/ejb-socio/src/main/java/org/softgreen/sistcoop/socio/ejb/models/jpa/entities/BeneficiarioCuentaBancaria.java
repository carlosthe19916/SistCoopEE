package org.softgreen.sistcoop.socio.ejb.models.jpa.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("cuentaBancaria")
public class BeneficiarioCuentaBancaria extends Beneficiario {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CuentaBancariaEntity cuentaBancaria;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public CuentaBancariaEntity getCuentaBancaria() {
		return cuentaBancaria;
	}

	public void setCuentaBancaria(CuentaBancariaEntity cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

}
