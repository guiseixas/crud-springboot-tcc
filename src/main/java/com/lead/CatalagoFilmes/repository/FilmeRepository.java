package com.lead.CatalagoFilmes.repository;

import com.lead.CatalagoFilmes.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.lead.CatalagoFilmes.model.Filme;
import java.util.List;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {

    @Query("SELECT f FROM Filme f WHERE LOWER(f.titulo) LIKE LOWER(CONCAT('%',:tituloFilme,'%')) ")
    List<Filme> searchName(String tituloFilme);

    List<Filme> findByCategoria(Categoria categoria);
}