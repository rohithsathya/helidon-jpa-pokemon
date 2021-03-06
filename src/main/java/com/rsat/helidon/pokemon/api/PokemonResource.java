package com.rsat.helidon.pokemon.api;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.rsat.helidon.pokemon.dto.PokemonDto;
import com.rsat.helidon.pokemon.exception.PokemonValidationException;
import com.rsat.helidon.pokemon.service.PokemonService;

import io.helidon.security.SecurityContext;

/**
 * A simple JAX-RS resource to Manage Pokemons. Examples:
 *
 * Get list of all Pokemons: curl -X GET http://localhost:8080/pokemon
 *
 * The message is returned as a JSON Array consisting of Pokemons.
 */
@Dependent
@Path("/pokemon")
public class PokemonResource {

	private static final Logger LOGGER = Logger.getLogger(PokemonResource.class.getName());

	private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

	@Inject
	private PokemonService pokemonService;


	/**
	 * Returns list of all the Pokemon in our DB.
	 *
	 * @return {@link JsonArray}
	 */
	@Operation(summary = "Returns list of all the Pokemon", 
	        description = "Returns list of all the Pokemon")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public Response getAllPokemons() {
		try {
			LOGGER.fine("API : Get Pokemon");
			JsonArrayBuilder builder = JSON.createArrayBuilder();
			List<PokemonDto> list = pokemonService.getAll();
			list.forEach(data -> builder.add(data.toJson()));
			Response response = Response.ok(builder.build()).build();
			return response;
		} catch (Exception e) {
			LOGGER.severe("Runtime System Exception");
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Internal Server Error, please contact admin").build();
		}
	}

	/**
	 * Returns the Pokemon for a given Id.
	 *
	 * @return {@link PokemonDto}
	 */
	@Operation(summary = "Returns the Pokemon for a given Id", 
	        description = "Returns the Pokemon for a given Id")
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public Response getById(@PathParam("id") String pokemonId) {
		LOGGER.fine("API : Get Pokemon/:id");
		try {
			PokemonDto pokemonDto = pokemonService.getById(pokemonId);
			if (pokemonDto != null) {
				return Response.ok(pokemonDto.toJson()).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("Did not find any pockemon for given id")
						.build();
			}
		} catch (Exception e) {
			LOGGER.severe("Runtime System Exception");
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Internal Server Error, please contact admin").build();
		}

	}

	

	/**
	 * Returns list of all Pokemons for a given Type.
	 *
	 * @return {@link JsonArray}
	 */
	@Operation(summary = "Returns list of all Pokemons for a given Type.", 
	        description = "Returns list of all Pokemons for a given Type.")
	@GET
	@Path("type/{pokemonType}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public Response getByType(@PathParam("pokemonType") String pokemonType) {
		LOGGER.fine("API : Get Pokemon by type /:type" + pokemonType);
		try {
			JsonArrayBuilder builder = JSON.createArrayBuilder();
			List<PokemonDto> list = pokemonService.getByType(pokemonType);
			list.forEach(data -> builder.add(data.toJson()));
			Response response = Response.ok(builder.build()).build();
			return response;

		} catch (Exception e) {
			LOGGER.severe("Runtime System Exception");
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Internal Server Error, please contact admin").build();
		}
	}

	/**
	 * Adds new Pokemon to our DB.
	 *
	 * @param jsonObject new pokemon to insert
	 * @return {@link JsonArray}
	 */
	@Operation(summary = "Adds given pokemon to system.", 
	        description = "Adds given pokemon to system.")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public Response save(final JsonObject pokemonJsonObj, @Context final SecurityContext context) {
		
		//TODO: Implement security and role check
		// String userId = getUserId(context);
		LOGGER.fine("API : Post Pokemon");
		try {
			PokemonDto pokemonDto = pokemonService.save(pokemonJsonObj);
			if (pokemonDto != null) {
				return Response.ok(pokemonDto.toJson()).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("System Error : Failed to create, please contact admin").build();
			}

		} catch (PokemonValidationException e) {
			LOGGER.fine("Validation Error");
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		} catch (Exception e) {
			LOGGER.severe("Runtime System Exception");
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Internal Error: Failed to create, please contact admin").build();
		}

	}

	/**
	 * Updates given Pokemon based on its Id.
	 *
	 * @param jsonObject new Pokemon to update
	 * @return {@link PokemonDto}
	 */
	@Operation(summary = "Updates given Pokemon based on its Id.", 
	        description = "Updates given Pokemon based on its Id.")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public Response update(final JsonObject pokemonJsonObj, @Context final SecurityContext context) {

		LOGGER.fine("API : Put Pokemon");
		//TODO: Implement security and role check
		// String userId = getUserId(context);
		
		try {
			PokemonDto pokemonDto = pokemonService.update(pokemonJsonObj);
			if (pokemonDto != null) {
				return Response.ok(pokemonDto.toJson()).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("Did not find pokemon with given id").build();
			}

		} catch (PokemonValidationException e) {
			LOGGER.fine("Validation Error");
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		} catch (Exception e) {
			LOGGER.severe("Runtime System Exception");
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Internal Error: Failed to create, please contact admin").build();
		}

	}
	
	/**
	 * Deletes given Pokemon based on its Id and returns number of Pokemons deleted.
	 *
	 * @return {@link Long}
	 */
	@Operation(summary = "Deletes given Pokemon based on its Id.", 
	        description = "Deletes given Pokemon based on its Id and returns number of Pokemons deleted.")
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public Response deleteById(@PathParam("id") String pokemonId) {
		LOGGER.fine("API : delete Pokemon/:id");
		try {
			long deletedNums = pokemonService.deleteById(pokemonId);
			return Response.ok(deletedNums).build();
		} catch (Exception e) {
			LOGGER.severe("Runtime System Exception");
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Internal Server Error, please contact admin").build();
		}

	}

}