package com.lead.CatalagoFilmes.repository;

import org.springframework.stereotype.Repository;
import com.lead.CatalagoFilmes.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
