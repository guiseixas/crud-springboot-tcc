package com.lead.CatalagoFilmes.service;

import org.springframework.stereotype.Service;

import com.lead.CatalagoFilmes.model.Categoria;
import com.lead.CatalagoFilmes.repository.CategoriaRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;
	
	public List<Categoria> findAll() { return categoriaRepository.findAll(); }
	
	public Optional<Categoria> findById(Long id) { return categoriaRepository.findById(id); }

	public Categoria save(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	public void deleteById(Long id) {
		categoriaRepository.deleteById(id);
	}

	public boolean verificaId(Long id){ return categoriaRepository.existsById(id); }

}
