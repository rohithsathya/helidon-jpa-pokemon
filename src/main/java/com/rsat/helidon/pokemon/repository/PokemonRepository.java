package com.rsat.helidon.pokemon.repository;

import java.util.List;

import com.rsat.helidon.pokemon.entity.Pokemon;

public interface PokemonRepository {

	List<Pokemon> getAll();
	
	List<Pokemon> getByType(String type);
	
	Pokemon getById(String id);
	
	Pokemon save(Pokemon pokemon);
	
	Pokemon update(Pokemon pokemon);
	
	long deleteById(String id);
}
