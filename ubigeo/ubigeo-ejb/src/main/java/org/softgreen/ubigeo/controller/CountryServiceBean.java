package org.softgreen.ubigeo.controller;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.softgreen.ubigeo.dao.DAO;
import org.softgreen.ubigeo.dao.QueryParameter;
import org.softgreen.ubigeo.entity.Country;
import org.softgreen.ubigeo.exception.NonexistentEntityException;
import org.softgreen.ubigeo.exception.PreexistingEntityException;
import org.softgreen.ubigeo.exception.RollbackFailureException;
import org.softgreen.ubigeo.service.CountryService;

@Named
@Stateless
@Remote(CountryService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CountryServiceBean implements CountryService {

	@Inject
	private DAO<Integer, Country> countryDAO;

	@Override
	public Country findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Country> findAll() {
		return countryDAO.findAll();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer create(Country t) throws PreexistingEntityException, RollbackFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, Country t) throws NonexistentEntityException, PreexistingEntityException, RollbackFailureException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer id) throws NonexistentEntityException, RollbackFailureException {
		// TODO Auto-generated method stub

	}

	@Override
	public Country findByAlpha2Code(String code) {
		QueryParameter queryParameter = QueryParameter.with("code", code);
		List<Country> list = countryDAO.findByNamedQuery(Country.findByAlpha2Code, queryParameter.parameters());
		if (list.size() <= 1) {
			Country result = null;
			for (Country country : list) {
				result = country;
			}
			return result;
		} else {
			return null;
		}
	}

	@Override
	public Country findByAlpha3Code(String code) {
		QueryParameter queryParameter = QueryParameter.with("code", code);
		List<Country> list = countryDAO.findByNamedQuery(Country.findByAlpha3Code, queryParameter.parameters());
		if (list.size() <= 1) {
			Country result = null;
			for (Country country : list) {
				result = country;
			}
			return result;
		} else {
			return null;
		}
	}

	@Override
	public Country findByNumericCode(String code) {
		QueryParameter queryParameter = QueryParameter.with("code", code);
		List<Country> list = countryDAO.findByNamedQuery(Country.findByNumericCode, queryParameter.parameters());
		if (list.size() <= 1) {
			Country result = null;
			for (Country country : list) {
				result = country;
			}
			return result;
		} else {
			return null;
		}
	}

}
