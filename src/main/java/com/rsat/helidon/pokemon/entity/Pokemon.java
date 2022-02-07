package com.rsat.helidon.pokemon.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rsat.helidon.pokemon.dto.PokemonDto;


@Access(value = AccessType.FIELD) 
@Entity(name = "Pokemon") 
@Table(name = "POKEMON") 
public class Pokemon implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id ;
	
	
	
	@Column(name="NAME",nullable = false, updatable = true,insertable = true)
	private String name;
	
	@Column(name="TYPE",nullable = false, updatable = true,insertable = true)
	private String type;
	
	@Column(name="AGE",nullable = false, updatable = true,insertable = true)
	private int age;
	
	
	
	
	 public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}




	public int getAge() {
		return age;
	}




	public void setAge(int age) {
		this.age = age;
	}

	
	public PokemonDto toDto(){
		
		PokemonDto dto = new PokemonDto();
		dto.setAge(age);
		dto.setId(id);
		dto.setName(name);
		dto.setType(type);
		return dto;
	}


	
}
