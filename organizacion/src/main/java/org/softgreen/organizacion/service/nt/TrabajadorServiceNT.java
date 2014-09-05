package org.softgreen.organizacion.service.nt;

import java.math.BigInteger;

import javax.ejb.Remote;

import org.softgreen.exception.NonexistentEntityException;
import org.softgreen.organizacion.entity.Agencia;
import org.softgreen.organizacion.entity.Caja;
import org.softgreen.organizacion.entity.Trabajador;

@Remote
public interface TrabajadorServiceNT extends AbstractServiceNT<Integer, Trabajador> {

	public Trabajador findByUsuario(String username);

	public Caja findByTrabajador(BigInteger idTrabajador) throws NonexistentEntityException;

	public Agencia getAgencia(BigInteger idTrabajador) throws NonexistentEntityException;

}
