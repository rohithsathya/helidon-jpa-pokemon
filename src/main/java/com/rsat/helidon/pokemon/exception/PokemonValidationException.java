package com.rsat.helidon.pokemon.exception;

public class PokemonValidationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PokemonValidationException(String errorMessage) {
		super(errorMessage);
	}

}
