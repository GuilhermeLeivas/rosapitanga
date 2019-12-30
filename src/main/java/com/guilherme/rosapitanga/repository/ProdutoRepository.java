package com.guilherme.rosapitanga.repository;

import com.guilherme.rosapitanga.model.Produto;
import com.guilherme.rosapitanga.repository.produto.ProdutoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQuery {


}
