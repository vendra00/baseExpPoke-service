package com.pokemonpedia.baseexppokeservice.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pokemonpedia.baseexppokeservice.dto.Pokemon;
import com.pokemonpedia.baseexppokeservice.exception.PokemonException;
import com.pokemonpedia.baseexppokeservice.model.BaseExpPokemon;
import com.pokemonpedia.baseexppokeservice.service.BaseExpPokemonService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/baseExpPokemon")
@Slf4j
public class BaseExpPokemonController {
	
	@Autowired
	private BaseExpPokemonService baseExpPokemonService;
	
	@PostMapping("saveBaseExpPokemon/")
	private BaseExpPokemon saveBaseExpPokemon(@RequestBody BaseExpPokemon p) throws PokemonException {
		log.info("Save Base Exp Pokemon Controller Call");
		try {
			return baseExpPokemonService.saveBaseExpPokemon(p);
		} catch (Exception e) {
			throw new PokemonException("There was a problem when saving this Pokemon");
		}
	}
	
	@GetMapping("/getAllHighestBaseExpPokemons")
	public List<Pokemon> getAllHighestBaseExpPokemons() throws PokemonException {
		log.info("Get All Highest Base Exp Pokemons Controller Call");
		try {
			return baseExpPokemonService.getAllBaseExpPokemons();
		} catch (Exception e) {
			throw new PokemonException("There was a problem at loading the Pokemons list from DB");
		}
	}
	
	@GetMapping("/getFiveHighestBaseExpPokemons")
	public ResponseEntity<Collection<BaseExpPokemon>> getFiveHeaviestPokemons() throws PokemonException {
		log.info("Get The 5 Highest Base Exp Pokemons Controller Call");
		try {
			return ResponseEntity.ok(baseExpPokemonService.getFiveHighestBaseExpPokemons());
		} catch (Exception e) {
			throw new PokemonException("There was a problem at loading the 5 Pokemons from DB");
		}
	}

}
