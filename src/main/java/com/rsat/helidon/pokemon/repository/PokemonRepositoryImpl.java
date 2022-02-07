package com.rsat.helidon.pokemon.repository;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.rsat.helidon.pokemon.entity.Pokemon;



@ApplicationScoped
public class PokemonRepositoryImpl implements PokemonRepository {

	private static final Logger LOGGER = Logger.getLogger(PokemonRepositoryImpl.class.getName());

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Pokemon> getAll() {
		LOGGER.fine("Repository : Get All Pokemon");

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pokemon> cq = cb.createQuery(Pokemon.class);
		Root<Pokemon> rootEntry = cq.from(Pokemon.class);
		CriteriaQuery<Pokemon> all = cq.select(rootEntry);
		TypedQuery<Pokemon> allQuery = entityManager.createQuery(all);
		List<Pokemon> pokemons = allQuery.getResultList();
		return pokemons;
	}

	@Override
	public List<Pokemon> getByType(String type) {
		LOGGER.fine("Repository : Get Pokemon by type : " + type);
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pokemon> cq = cb.createQuery(Pokemon.class);
		Root<Pokemon> rootEntry = cq.from(Pokemon.class);
		CriteriaQuery<Pokemon> typeQuery = cq.select(rootEntry).where(cb.equal(rootEntry.get("type"), type));

		TypedQuery<Pokemon> allQuery = entityManager.createQuery(typeQuery);
		List<Pokemon> pokemons = allQuery.getResultList();
		return pokemons;
	}

	@Override
	public Pokemon getById(String id) {
		LOGGER.fine("Repository : Get Pokemon by Id : " + id);
		Pokemon pokemon = this.entityManager.find(Pokemon.class, id);
		return pokemon;
	}

	@Override
	public Pokemon save(Pokemon pokemon) {
		LOGGER.fine("Repository : insert Pokemon ");
		String newId = UUID.randomUUID().toString();
		LOGGER.fine("Repository : insertt Pokemon auto generated id : " + newId);
		pokemon.setId(newId);
		this.entityManager.persist(pokemon);
		return pokemon;
	}

	@Override
	public Pokemon update(Pokemon pokemon) {
		LOGGER.fine("Repository : update Pokemon ");
		this.entityManager.merge(pokemon);
		return pokemon;
	}

	@Override
	public long deleteById(String Id) {
		Pokemon pokemon =  getById(Id);
		if(pokemon != null) {
			this.entityManager.remove(pokemon);
			return 1;
		}else {
			return 0;
		}
		
	}

	

}
