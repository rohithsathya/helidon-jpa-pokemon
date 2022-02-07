package com.rsat.helidon.pokemon.dto;

import java.util.Objects;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.rsat.helidon.pokemon.entity.Pokemon;
import com.rsat.helidon.pokemon.exception.PokemonValidationException;
import com.rsat.helidon.pokemon.util.PokemonValidation;

public class PokemonDto {
	private String id;
	private String name;
	private String type;
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

	@Override
	public int hashCode() {
		return Objects.hash(age, id, name, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PokemonDto other = (PokemonDto) obj;
		return age == other.age && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "PokemonDto [id=" + id + ", name=" + name + ", type=" + type + ", age=" + age + "]";
	}

	public JsonObject toJson() {

		// to send over to rest
		JsonObjectBuilder builder = Json.createObjectBuilder();
		return builder.add("id", id).add("name", name).add("type", type).add("age", age).build();
	}

	public Pokemon toEntity() {

		Pokemon entity = new Pokemon();
		entity.setAge(age);
		entity.setId(id);
		entity.setName(name);
		entity.setType(type);
		return entity;
	}

	public static PokemonDto getInstanceFromJson(final JsonObject jsonObject) throws PokemonValidationException {

		PokemonDto result = new PokemonDto();
		try {
		
			String id = jsonObject.containsKey("id") ? jsonObject.getString("id") : null;
			String name = jsonObject.containsKey("name") ? jsonObject.getString("name") : null;
			String type = jsonObject.containsKey("type") ? jsonObject.getString("type") : null;
			int age = jsonObject.containsKey("age") ? jsonObject.getInt("age") : -1;
			result.setId(id);
			result.setName(name);
			result.setAge(age);
			result.setType(type);
		} catch (Exception e) {
			throw new PokemonValidationException("Json parse error : Please enter a valid json");
		}
		result.validate();
		return result;
	}

	// validation

	public void validate() throws PokemonValidationException {

		PokemonValidation.validateName(name);
		PokemonValidation.validateType(type);
		PokemonValidation.validateAge(age);
	}

}