package com.guilherme.rosapitanga.repository;

import com.guilherme.rosapitanga.model.Categoria;
import com.guilherme.rosapitanga.repository.categoria.CategoriaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>, CategoriaRepositoryQuery {
}
