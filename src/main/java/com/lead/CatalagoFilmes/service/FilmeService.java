package com.lead.CatalagoFilmes.service;


import com.lead.CatalagoFilmes.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lead.CatalagoFilmes.model.Filme;
import com.lead.CatalagoFilmes.repository.FilmeRepository;
import java.util.List;
import java.util.Optional;

@Service
public class FilmeService {

	@Autowired
	private FilmeRepository filmeRepository;

	@Autowired
	private CategoriaService categoriaService;

	public List<Filme> findAll() {
		return filmeRepository.findAll();
	}

	public Optional<Filme> findById(Long id) {
		return filmeRepository.findById(id);
	}

	public Filme save(Filme filme) {
		return filmeRepository.save(filme);
	}

	public Filme update(Filme filme) {
		return filmeRepository.save(filme);
	}

	public void deleteById(Long id) { filmeRepository.deleteById(id);}

	public boolean verificaId(Long id){ return filmeRepository.existsById(id); }

	public List<Filme> searchName(String tituloFilme){ return filmeRepository.searchName(tituloFilme); }

	public List<Filme> findByCategoria(Long id){
		Optional<Categoria> categoria = categoriaService.findById(id);
		if(categoria.isPresent()) {
			return filmeRepository.findByCategoria(categoria.get());
		}
		return null;
	}
}
