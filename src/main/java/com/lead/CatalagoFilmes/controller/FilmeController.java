package com.lead.CatalagoFilmes.controller;

import java.util.List;
import java.util.Optional;

import com.lead.CatalagoFilmes.model.Categoria;
import com.lead.CatalagoFilmes.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lead.CatalagoFilmes.model.Filme;
import com.lead.CatalagoFilmes.service.FilmeService;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/filme")
public class FilmeController {

	@Autowired
	private FilmeService filmeService;

	@GetMapping("/filmes")
	public ResponseEntity<?> listaFilmes() {
		try{
			List<Filme> filmes = filmeService.findAll();
			if(filmes.isEmpty()){
				return new ResponseEntity<>("Não existem filmes cadastrados.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(filmes, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/filmeBusca/{tituloFilme}")
	public ResponseEntity<?> searchName(@PathVariable String tituloFilme) {
		try{
			List<Filme> filmes = filmeService.searchName(tituloFilme);
			if(filmes.isEmpty()){
				return new ResponseEntity<String>("Não existe filme com esse título.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Filme>>(filmes, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/filmeById/{id}")
	public ResponseEntity<?> getFilmeById(@PathVariable Long id) {
		try{
			Optional<Filme> filme = filmeService.findById(id);
			if(filme.isEmpty()){
				return new ResponseEntity<>("Não existe filme com esse id.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(filme, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/salvaFilme")
	public ResponseEntity<?> salvaFilme(@RequestBody @Valid Filme filme) {
		try{
			Filme filmeSalvo = filmeService.save(filme);
			return new ResponseEntity<Filme>(filmeSalvo, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/atualizaFilme")
	public ResponseEntity<?> atualizaFilme(@RequestBody @Valid Filme filme) {
		try{
			Filme filmeAtualizado = filmeService.update(filme);
			if(!filmeService.verificaId(filmeAtualizado.getId())){
				return new ResponseEntity<String>("Não existe esse filme.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Filme>(filmeAtualizado, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteFilmeById/{id}")
	public ResponseEntity<?> deleteFilmeById(@PathVariable Long id) {
		try{
			if(!filmeService.verificaId(id)){
				return new ResponseEntity<String>("Não existe filme com esse id.", HttpStatus.NOT_FOUND);
			}
			filmeService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getFilmesByCategoria/{id}")
	public ResponseEntity<?> findByCategoria(@PathVariable Long id){
		try{
			List<Filme> filmes = filmeService.findByCategoria(id);
			if(filmes.isEmpty()){
				return new ResponseEntity<String>("Não existem categorias cadastradas com esse id.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Filme>>(filmes, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
