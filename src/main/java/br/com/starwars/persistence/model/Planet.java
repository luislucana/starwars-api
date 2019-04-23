package br.com.starwars.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "planet")
public class Planet implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;
	
	@NotNull
	@Column(name = "name", nullable = false)
    private String name;
	
	@NotNull
	@Column(name = "climate", nullable = false)
    private String climate;
	
	@NotNull
	@Column(name = "terrain", nullable = false)
    private String terrain;
	
	@NotNull
	@Column(name = "films_quantity", nullable = false)
	private Integer filmsQuantity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public Integer getFilmsQuantity() {
		return filmsQuantity;
	}

	public void setFilmsQuantity(Integer filmsQuantity) {
		this.filmsQuantity = filmsQuantity;
	}
}
