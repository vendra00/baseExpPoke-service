package com.pokemonpedia.baseexppokeservice.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.pokemonpedia.baseexppokeservice.dto.Pokemon;
import com.pokemonpedia.baseexppokeservice.model.BaseExpPokemon;
import com.pokemonpedia.baseexppokeservice.repository.BaseExpPokemonRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BaseExpPokemonServiceImpl implements BaseExpPokemonService{
	
	@Autowired
	private BaseExpPokemonRepository baseExpPokemonRepository; 
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String GET_ALL_POKEMONS_DB = "http://localhost:8090/pokemon/getAllPokemonsDb";
	
	@Override
	public BaseExpPokemon saveBaseExpPokemon(BaseExpPokemon p) {
		log.info("Save Base Exp Pokemon Service Call");
		return baseExpPokemonRepository.save(p);
	}

	@Override
	public List<Pokemon> getAllBaseExpPokemons() {
		ResponseEntity<Pokemon[]> response = getPokemonsFromPokeService();
		List<Pokemon> list = Arrays.asList(response.getBody());
		
		findHighestFiveBaseExpPokemons(list);
		
		return list;
	}

	@Override
	public List<BaseExpPokemon> getFiveHighestBaseExpPokemons() {
		if (baseExpPokemonRepository.findAll().isEmpty()) {
			getAllBaseExpPokemons();
		}
		return baseExpPokemonRepository.findAll().stream().collect(Collectors.toList());
	}
	
	private ResponseEntity<Pokemon[]> getPokemonsFromPokeService() throws RestClientException {
		log.info("Get All Base Exp Pokemons Service Call");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		ResponseEntity<Pokemon[]> response = restTemplate.exchange(GET_ALL_POKEMONS_DB, HttpMethod.GET, entity,
				Pokemon[].class);
		return response;
	}
	
	private void findHighestFiveBaseExpPokemons(List<Pokemon> list) {
		log.info("Find The 5 Highest Base Exp Pokemons Service Call");
		sortPokemonsByBaseExp(list);
		getTheFiveHighestBaseExpPokemonsAndSave(list);

	}
	
	private void getTheFiveHighestBaseExpPokemonsAndSave(List<Pokemon> list) {
		log.info("Find The 5 Highest Base Exp Pokemons And Save Service Call");
		if (baseExpPokemonRepository.findAll().isEmpty()) {
			saveBaseExpPokemon(list);
		}
	}
	
	private void saveBaseExpPokemon(List<Pokemon> list) {
		log.info("Save A Base Exp Pokemon And Save On DB Service Call");
		for (int i = 0; i <= 4; i++) {
			BaseExpPokemon p = new BaseExpPokemon();
			p.setName(list.get(i).getName());
			p.setWeight(list.get(i).getWeight());
			p.setHeight(list.get(i).getHeight());
			p.setBase_experience(list.get(i).getBase_experience());
			saveBaseExpPokemon(p);
		}
	}
	
	private void sortPokemonsByBaseExp(List<Pokemon> list) {
		log.info("Sort All Pokemons By Base Exp Service Call");
		Collections.sort(list, new Comparator<Pokemon>() {
			@Override
			public int compare(Pokemon o1, Pokemon o2) {
				return Integer.valueOf(o2.getBase_experience()).compareTo(o1.getBase_experience());
			}
		});
	}

}
