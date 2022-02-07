package com.rsat.helidon.pokemon.util;

import com.rsat.helidon.pokemon.exception.PokemonValidationException;

public class PokemonValidation {

	public static void validateName(String name) throws PokemonValidationException {
		boolean isvalid =  (name !=null && (name.length() > 2 && name.length() <= 200)) ? true : false;
		if(!isvalid) {
			throw new PokemonValidationException(
					"Type should be at least 3 characters long and maximum of 200 characters");
		}
		
	}
	public static void validateType(String type) throws PokemonValidationException {
		boolean isvalid = (type !=null && (type.length() > 0 && type.length() <= 200)) ? true : false;
		if(!isvalid) {
			throw new PokemonValidationException(
					"Type should be at least 1 characters long and maximum of 200 characters");
		}
	}
	
	public static void validateAge(int age) throws PokemonValidationException {
		boolean isvalid =  (age >= 0 && age <= 100) ? true : false;
		if(!isvalid) {
			throw new PokemonValidationException(
					"Age should be between 0 and 100");
		}
	}
}
