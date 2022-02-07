package com.rsat.helidon.pokemon.service;

import java.util.List;

import javax.json.JsonObject;

import com.rsat.helidon.pokemon.dto.PokemonDto;
import com.rsat.helidon.pokemon.exception.PokemonValidationException;


public interface PokemonService {

	List<PokemonDto> getAll();

	List<PokemonDto> getByType(String type);

	PokemonDto getById(String id);

	PokemonDto save(JsonObject pokemonJsonObj) throws PokemonValidationException;

	PokemonDto update(JsonObject pokemonJsonObj) throws PokemonValidationException;

	long deleteById(String id);
}
