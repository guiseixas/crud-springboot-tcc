package com.lead.CatalagoFilmes.controller;

import java.util.List;
import java.util.Optional;

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
import com.lead.CatalagoFilmes.service.CategoriaService;
import com.lead.CatalagoFilmes.model.Categoria;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping("/categorias")
	public ResponseEntity<?> listaCategorias() {
		try{
			List<Categoria> categorias = categoriaService.findAll();
			if(categorias.isEmpty()){
				return new ResponseEntity<String>("Não existem categorias cadastradas.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Categoria>>(categorias, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/categoriaById/{id}")
	public ResponseEntity<?> getCategoriaById(@PathVariable Long id) {
		try {
			Optional<Categoria> categoria = categoriaService.findById(id);
			if(categoria.isEmpty()){
				return new ResponseEntity<String>("Não existe categoria com esse id.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Optional<Categoria>>(categoria, HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/salvaCategoria")
	public ResponseEntity<?> salvaCategoria(@RequestBody @Valid Categoria categoria) {
		try{
			Categoria categoriaSalva = categoriaService.save(categoria);
			return new ResponseEntity<Categoria>(categoriaSalva, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/atualizaCategoria")
	public ResponseEntity<?> atualizaCategoria(@RequestBody @Valid Categoria categoria) {
		try{
			Categoria categoriaAtualizada = categoriaService.update(categoria);
			if(!categoriaService.verificaId(categoriaAtualizada.getId())){
				return new ResponseEntity<String>("Não existe essa categoria.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Categoria>(categoriaAtualizada, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteCategoriaById/{id}")
	public ResponseEntity<?> deleteCategoriaById(@PathVariable Long id) {
		try{
			if(!categoriaService.verificaId(id)){
				return new ResponseEntity<String>("Não existe categoria com esse id.", HttpStatus.NOT_FOUND);
			}
			categoriaService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}