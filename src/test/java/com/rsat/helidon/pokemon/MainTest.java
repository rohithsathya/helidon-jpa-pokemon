
package com.rsat.helidon.pokemon;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.helidon.microprofile.tests.junit5.HelidonTest;

@HelidonTest
class MainTest {

	@Inject
	private WebTarget target;

	@Test
	void testHelloWorld() {
		// get test
		JsonArray pokemonsList = target.path("pokemon").request().get(JsonArray.class);
		Assertions.assertNotNull(pokemonsList);
		Assertions.assertEquals(3, pokemonsList.size(), "default size check");

		// get by id test
		JsonObject pokemonWithId1 = target.path("pokemon/p1").request().get(JsonObject.class);
		Assertions.assertNotNull(pokemonWithId1);

		Assertions.assertEquals(8, pokemonWithId1.getInt("age"), "Age check");

		// get by id test
		try (Response r = target.path("pokemon/id9").request().get()) {
			Assertions.assertEquals(404, r.getStatus(), "Not found check");
		}

		// get by id type
		JsonArray pokemonWithTypeT1 = target.path("pokemon/type/Flying").request().get(JsonArray.class);
		Assertions.assertNotNull(pokemonWithTypeT1);

		Assertions.assertEquals(1, pokemonWithTypeT1.size(), "size check");

		JsonArray pokemonWithTypeTest = target.path("pokemon/type/Test").request().get(JsonArray.class);
		Assertions.assertNotNull(pokemonWithTypeTest);

		Assertions.assertEquals(0, pokemonWithTypeTest.size(), "size check");

		// post
		JsonObject createPokemonReqBody = Json.createObjectBuilder().add("name", "test1").add("age", 20)
				.add("type", "T2").build();

		JsonObject pokemonPostResponse = target.path("pokemon").request().post(Entity.json(createPokemonReqBody),
				JsonObject.class);

		Assertions.assertNotNull(pokemonPostResponse);

		// update put
		String pokemonNewId = pokemonPostResponse.getString("id");
		JsonObject putPokemonReqBody = Json.createObjectBuilder().add("id", pokemonNewId).add("age", 80)
				.add("type", "T1").build();

		JsonObject pockemonPut = target.path("pokemon").request().put(Entity.json(putPokemonReqBody), JsonObject.class);
		Assertions.assertNotNull(pockemonPut);
		
		// get by id test
		long numDeleted = target.path("pokemon/"+pokemonNewId).request().delete(Long.class);
		Assertions.assertEquals(1, numDeleted, "delete check");
		
		//metrics
		try (Response r = target.path("metrics").request().get()) {
			Assertions.assertEquals(200, r.getStatus(), "GET metrics status code");
		}

		//health
		try (Response r = target.path("health").request().get()) {
			Assertions.assertEquals(200, r.getStatus(), "GET health status code");
		}
	}
}
