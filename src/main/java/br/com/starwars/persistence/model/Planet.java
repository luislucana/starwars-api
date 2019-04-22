package br.com.starwars.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "planet")
public class Planet implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private Integer id;
	
	@Column(name = "name", nullable = false)
    private String name;
	
	@Column(name = "climate", nullable = false)
    private String climate;
	
	@Column(name = "terrain", nullable = false)
    private String terrain;
}
