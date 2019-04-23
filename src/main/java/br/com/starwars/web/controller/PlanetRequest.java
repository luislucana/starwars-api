package br.com.starwars.web.controller;

import java.io.Serializable;

/**
 * Planet request body
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class PlanetRequest implements Serializable {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
