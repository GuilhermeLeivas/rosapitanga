package com.guilherme.rosapitanga.repository;

import com.guilherme.rosapitanga.model.Crediario;
import com.guilherme.rosapitanga.repository.crediario.CrediarioRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrediarioRepository extends JpaRepository<Crediario, Long>, CrediarioRepositoryQuery {
}
