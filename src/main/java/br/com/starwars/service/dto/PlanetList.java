package br.com.starwars.service.dto;

import java.util.List;

/**
 * 
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class PlanetList {
	
	private String next;

	private String previous;

	private String count;

	private List<Result> results;

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}
	
	@Override
	public String toString() {
		String retorno = "PlanetList[";
		
		if (results == null || results.isEmpty()) {
			return "(empty)";
		}
		
		String elemento = null;
		
		for (Result result : results) {
			elemento = String.valueOf(result);
		}
		
		retorno = retorno + elemento + ", ";
		
		return retorno;
	}
}
