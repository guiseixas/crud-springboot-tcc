package com.lead.CatalagoFilmes.controller;

import java.util.List;
import java.util.Optional;

import com.lead.CatalagoFilmes.model.Categoria;
import com.lead.CatalagoFilmes.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
import com.lead.CatalagoFilmes.model.Idioma;
import com.lead.CatalagoFilmes.service.IdiomaService;

import javax.persistence.Id;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/idioma")
public class IdiomaController {

	@Autowired
	private IdiomaService idiomaService;

	@GetMapping("/idiomas")
	public ResponseEntity<?> listaIdiomas() {
		try {
			List<Idioma> idiomas = idiomaService.findAll();
			if(idiomas.isEmpty()){
				return new ResponseEntity<String>("Não existem idiomas cadastrados.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Idioma>>(idiomas, HttpStatus.OK);
		} catch(Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/idiomaById/{id}")
	public ResponseEntity<?> getIdiomaById(@PathVariable Long id) {
		try{
			Optional<Idioma> idioma = idiomaService.findById(id);
			if(idioma.isEmpty()){
				return new ResponseEntity<String>("Não existe idioma com esse id.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Optional<Idioma>>(idioma, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/salvaIdioma")
	public ResponseEntity<?> salvaIdioma(@RequestBody @Valid Idioma idioma) {
		try{
			Idioma idiomaSalvo = idiomaService.save(idioma);
			return new ResponseEntity<Idioma>(idiomaSalvo, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/atualizaIdioma")
	public ResponseEntity<?> atualizaIdioma(@RequestBody @Valid Idioma idioma) {
		try{
			Idioma idiomaAtualizado = idiomaService.update(idioma);
			if(!idiomaService.verificaId(idiomaAtualizado.getId())){
				return new ResponseEntity<String>("Não existe esse idioma.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Idioma>(idiomaAtualizado, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteIdiomaById/{id}")
	public ResponseEntity<?> deleteIdiomaById(@PathVariable Long id) {
		try{
			if(!idiomaService.verificaId(id)){
				return new ResponseEntity<String>("Não existe idioma com esse id.", HttpStatus.NOT_FOUND);
			}
			idiomaService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}