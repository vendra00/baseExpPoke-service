package com.pokemonpedia.baseexppokeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pokemonpedia.baseexppokeservice.model.BaseExpPokemon;

@Repository
public interface BaseExpPokemonRepository extends JpaRepository<BaseExpPokemon, Long>{

}
