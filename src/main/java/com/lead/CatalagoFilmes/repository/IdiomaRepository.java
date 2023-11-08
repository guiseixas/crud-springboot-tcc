package com.lead.CatalagoFilmes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lead.CatalagoFilmes.model.Idioma;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, Long> {
}
