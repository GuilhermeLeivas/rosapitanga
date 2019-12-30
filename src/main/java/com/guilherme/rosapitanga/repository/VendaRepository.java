package com.guilherme.rosapitanga.repository;

import com.guilherme.rosapitanga.model.Venda;
import com.guilherme.rosapitanga.repository.venda.VendaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long>, VendaRepositoryQuery {
}
