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
import com.lead.CatalagoFilmes.model.Usuario;
import com.lead.CatalagoFilmes.service.UsuarioService;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/usuarios")
	public ResponseEntity<?> listaUsuarios() {
		try{
			List<Usuario> usuarios = usuarioService.findAll();
			if(usuarios.isEmpty()){
				return new ResponseEntity<String>("Não existem usuários cadastrados.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/usuarioById/{id}")
	public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
		try{
			Optional<Usuario> usuario = usuarioService.findById(id);
			if(usuario.isEmpty()){
				return new ResponseEntity<String>("Não existe usuário com esse id.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Optional<Usuario>>(usuario, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/salvaUsuario")
	public ResponseEntity<?> salvaUsuario(@RequestBody @Valid Usuario usuario) {
		try{
			Usuario usuarioSalvo = usuarioService.save(usuario);
			return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/atualizaUsuario")
	public ResponseEntity<?> atualizaUsuario(@RequestBody @Valid Usuario usuario) {
		try {
			Usuario usuarioAtualizado = usuarioService.update(usuario);
			if(!usuarioService.verificaId(usuarioAtualizado.getId())){
				return new ResponseEntity<String>("Não existe esse usuario.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Usuario>(usuarioAtualizado, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteUsuarioById/{id}")
	public ResponseEntity<?> deleteUsuarioById(@PathVariable Long id) {
		try{
			if(!usuarioService.verificaId(id)){
				return new ResponseEntity<String>("Não existe usuário com esse id.", HttpStatus.NOT_FOUND);
			}
			usuarioService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
