package com.rsat.helidon.pokemon.api;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.json.Json;
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

import com.rsat.helidon.pokemon.GreetResource;
import com.rsat.helidon.pokemon.dto.PokemonDto;
import com.rsat.helidon.pokemon.exception.PokemonValidationException;
import com.rsat.helidon.pokemon.service.PokemonService;

import io.helidon.security.SecurityContext;

/**
 * A simple JAX-RS resource to greet you. Examples:
 *
 * Get default greeting message: curl -X GET http://localhost:8080/greet
 *
 * The message is returned as a JSON object.
 */
@Dependent
@Path("/pokemon")
public class PokemonResource {

	private static final Logger LOGGER = Logger.getLogger(GreetResource.class.getName());

//	@PersistenceContext
//	private EntityManager em;
//
	private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());
//
//	private final String message;
//
//	@Inject
//	private PokemonRepository pokemonRepository;

	@Inject
	private PokemonService pokemonService;

//	@Inject
//	public GreetResource(@ConfigProperty(name = "app.greeting") String message) {
//		this.message = message;
//	}

	/**
	 * Return a worldly greeting message.
	 *
	 * @return {@link JsonObject}
	 */
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public JsonObject getDefaultMessage() {
//        String msg = String.format("%s %s!", message, "World");
//        return JSON.createObjectBuilder()
//                .add("message", msg)
//                .build();
//    }

	@GET
//	@Path("pokemon")
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

	

	@GET
	@Path("type/{pokemonType}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public Response getByType(@PathParam("pokemonType") String pokemonType) {
		LOGGER.fine("API : Get Pokemon by type /:type");
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
	 * Create a new TODO entry.
	 * 
	 * @param jsonObject the value of the new entry
	 * @param context    security context to map the user
	 * @return the response ({@code 200} status if successful
	 */
	@POST
	// @Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public Response save(final JsonObject pokemonJsonObj, @Context final SecurityContext context) {

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

	@PUT
	// @Path("{pokemonId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	// @PathParam("pokemonId") String pokemonId,
	public Response update(final JsonObject pokemonJsonObj, @Context final SecurityContext context) {

		LOGGER.fine("API : Put Pokemon");
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
	// deleteById

}