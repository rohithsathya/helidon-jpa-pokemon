package com.rsat.helidon.pokemon.service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.transaction.Transactional;

import com.rsat.helidon.pokemon.dto.PokemonDto;
import com.rsat.helidon.pokemon.entity.Pokemon;
import com.rsat.helidon.pokemon.exception.PokemonValidationException;
import com.rsat.helidon.pokemon.repository.PokemonRepository;
import com.rsat.helidon.pokemon.util.PokemonValidation;


@ApplicationScoped
public class PokemonServiceImpl implements PokemonService {

	private static final Logger LOGGER = Logger.getLogger(PokemonServiceImpl.class.getName());
	@Inject
	private PokemonRepository pokemonRepository;

	@Override
	@Transactional
	public List<PokemonDto> getAll() {
		LOGGER.fine("Service : Get All Pokemon");
		return pokemonRepository.getAll().stream().map(p -> p.toDto()).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public List<PokemonDto> getByType(String type) {
		LOGGER.fine("Service : Get All Pokemon by type : " + type);
		return pokemonRepository.getByType(type).stream().map(p -> p.toDto()).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public PokemonDto getById(String id) {
		LOGGER.fine("Service : Get Pokemon by Id : " + id);
		Pokemon pokemon =  pokemonRepository.getById(id);
		return pokemon !=null ? pokemon.toDto() : null;
	}

	@Override
	@Transactional
	public PokemonDto save(JsonObject pokemonJsonObj) throws PokemonValidationException {
		LOGGER.fine("Service : insert Pokemon");
		PokemonDto dto = PokemonDto.getInstanceFromJson(pokemonJsonObj);
		LOGGER.fine("Service : Pokemon to insert " + dto);
		Pokemon pokemon =  pokemonRepository.save(dto.toEntity());
		return pokemon !=null ? pokemon.toDto() : null;
	}

	@Override
	@Transactional
	public PokemonDto update(JsonObject pokemonJsonObj) throws PokemonValidationException {
		LOGGER.fine("Service : Update Pokemon ");
		Pokemon pokemon = null;
		PokemonDto pokemonDto = null;
		if (pokemonJsonObj.containsKey("id")) {
			String id =  pokemonJsonObj.getString("id");
			pokemon =  pokemonRepository.getById(id);
		}
		
		if(pokemon != null) {
			
			if (pokemonJsonObj.containsKey("name")) {
				PokemonValidation.validateName(pokemonJsonObj.getString("name"));
				pokemon.setName(pokemonJsonObj.getString("name"));
			}
			if (pokemonJsonObj.containsKey("type")) {
				PokemonValidation.validateType(pokemonJsonObj.getString("type"));
				pokemon.setType(pokemonJsonObj.getString("type"));
			}
			if (pokemonJsonObj.containsKey("age")) {
				PokemonValidation.validateAge(pokemonJsonObj.getInt("age"));
				pokemon.setAge(pokemonJsonObj.getInt("age"));
			}
			pokemon = pokemonRepository.update(pokemon);
			pokemonDto = pokemon !=null ? pokemon.toDto() : null;
		}
		return pokemonDto;
	}

	@Override
	@Transactional
	public long deleteById(String id) {
		LOGGER.fine("Service : Delete Pokemon by Id : " + id);
		return pokemonRepository.deleteById(id);
	}

}
