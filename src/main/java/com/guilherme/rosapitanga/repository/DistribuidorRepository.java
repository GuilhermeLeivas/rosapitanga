package com.guilherme.rosapitanga.repository;

import com.guilherme.rosapitanga.model.Distribuidor;
import com.guilherme.rosapitanga.repository.distribuidor.DistribuidorRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistribuidorRepository extends JpaRepository<Distribuidor, Long>, DistribuidorRepositoryQuery {
}
