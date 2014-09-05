package org.softgreen.ubigeo.controller;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.softgreen.dao.DAO;
import org.softgreen.dao.QueryParameter;
import org.softgreen.ubigeo.entity.Country;
import org.softgreen.ubigeo.entity.SubDivision;

@Stateless
public class UbigeoController {

	@Inject
	private DAO<Integer, Country> countryDAO;
	
	@Inject
	private DAO<String, SubDivision> subDivisionDAO;
		
	public TreeSet<Country> getCountries() {
		Collection<Country> result = countryDAO.findByNamedQuery(Country.findAll);
		return new TreeSet<Country>(result);
	}
	
	public Country getCountryByAlpha2Code(String code){
		QueryParameter queryParameter = QueryParameter.with("code", code);
		List<Country> list = countryDAO.findByNamedQuery(Country.findByAlpha2Code, queryParameter.parameters());
		if(list.size() <= 1){
			Country result = null;
			for (Country country : list) {
				result = country;
			}
			return result;
		} else {
			return null;
		}		
	}
	
	public Country getCountryByAlpha3Code(String code){
		QueryParameter queryParameter = QueryParameter.with("code", code);
		List<Country> list = countryDAO.findByNamedQuery(Country.findByAlpha3Code, queryParameter.parameters());
		if(list.size() <= 1){
			Country result = null;
			for (Country country : list) {
				result = country;
			}
			return result;
		} else {
			return null;
		}	
	}
	
	public Country getCountryByNumericCode(String code){
		QueryParameter queryParameter = QueryParameter.with("code", code);
		List<Country> list = countryDAO.findByNamedQuery(Country.findByNumericCode, queryParameter.parameters());
		if(list.size() <= 1){
			Country result = null;
			for (Country country : list) {
				result = country;
			}
			return result;
		} else {
			return null;
		}	
	}

	public TreeSet<SubDivision> getSubDivisionsByAlpha2Code(String countryCode, String subDivisionCode) {
		QueryParameter queryParameter = QueryParameter.with("countryCode", countryCode).and("subDivisionCode", subDivisionCode);
		Collection<SubDivision> result = subDivisionDAO.findByNamedQuery(SubDivision.findAllByAlpha2Code, queryParameter.parameters());
		return new TreeSet<SubDivision>(result);
	}
	
	public TreeSet<SubDivision> getSubDivisionsByAlpha3Code(String countryCode, String subDivisionCode) {
		QueryParameter queryParameter = QueryParameter.with("countryCode", countryCode).and("subDivisionCode", subDivisionCode);
		Collection<SubDivision> result = subDivisionDAO.findByNamedQuery(SubDivision.findAllByAlpha3Code, queryParameter.parameters());
		return new TreeSet<SubDivision>(result);
	}
	
	public TreeSet<SubDivision> getSubDivisionsByNumericCode(String countryCode, String subDivisionCode) {
		QueryParameter queryParameter = QueryParameter.with("countryCode", countryCode).and("subDivisionCode", subDivisionCode);
		Collection<SubDivision> result = subDivisionDAO.findByNamedQuery(SubDivision.findAllByNumericCode, queryParameter.parameters());
		return new TreeSet<SubDivision>(result);
	}

}
