package org.softgreen.ubigeo.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.softgreen.dao.DAO;
import org.softgreen.dao.QueryParameter;
import org.softgreen.ubigeo.entity.Currency;
import org.softgreen.ubigeo.entity.ExchangeRate;

@Stateless
public class TasaCambioController {

	@Inject
	private DAO<String, Currency> monedaDAO;

	@Inject
	private DAO<Long, ExchangeRate> tasaCambioDAO;

	public BigDecimal getTasaCambio(String codigoMonedaOrigen, String codigoMonedaDestino) {
		Currency monedaOrigen = monedaDAO.find(codigoMonedaOrigen);
		Currency monedaDestino = monedaDAO.find(codigoMonedaDestino);
		if (monedaOrigen == null)
			return null;
		if (monedaDestino == null)
			return null;

		Calendar calendar = Calendar.getInstance();
		Date fecha = calendar.getTime();

		QueryParameter queryParameter = QueryParameter.with("codigoMonedaOrigen", codigoMonedaOrigen).and("codigoMonedaDestino", codigoMonedaDestino).and("fecha", fecha);
		List<ExchangeRate> list = tasaCambioDAO.findByNamedQuery(ExchangeRate.findByCurrencyOriginDestiny, queryParameter.parameters());

		if (list.size() <= 1) {
			BigDecimal result = null;
			for (ExchangeRate tasaCambio : list) {
				result = tasaCambio.getValor();
			}
			return result;
		} else {
			System.out.println("Error en base de datos");
			return null;
		}
	}

	public BigDecimal getTasaCambio(String codigoMonedaOrigen, String codigoMonedaDestino, Date fecha) {
		Currency monedaOrigen = monedaDAO.find(codigoMonedaOrigen);
		Currency monedaDestino = monedaDAO.find(codigoMonedaDestino);
		if (monedaOrigen == null)
			return null;
		if (monedaDestino == null)
			return null;
		if (fecha == null)
			return null;

		QueryParameter queryParameter = QueryParameter.with("codigoMonedaOrigen", codigoMonedaOrigen).and("codigoMonedaDestino", codigoMonedaDestino).and("fecha", fecha);
		List<ExchangeRate> list = tasaCambioDAO.findByNamedQuery(ExchangeRate.findByCurrencyOriginDestiny, queryParameter.parameters());

		if (list.size() <= 1) {
			BigDecimal result = null;
			for (ExchangeRate tasaCambio : list) {
				result = tasaCambio.getValor();
			}
			return result;
		} else {
			System.out.println("Error en base de datos");
			return null;
		}
	}

}
