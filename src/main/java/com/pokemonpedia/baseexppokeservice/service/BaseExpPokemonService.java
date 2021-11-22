package com.pokemonpedia.baseexppokeservice.service;

import java.util.List;

import com.pokemonpedia.baseexppokeservice.dto.Pokemon;
import com.pokemonpedia.baseexppokeservice.model.BaseExpPokemon;

public interface BaseExpPokemonService {
	
	BaseExpPokemon saveBaseExpPokemon(BaseExpPokemon p);
	
	List<Pokemon> getAllBaseExpPokemons();
	
	List<BaseExpPokemon> getFiveHighestBaseExpPokemons();

}
