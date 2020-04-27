package com.guilherme.rosapitanga.repository;

import com.guilherme.rosapitanga.model.Produto;
import com.guilherme.rosapitanga.repository.produto.ProdutoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQuery {

    @Query("UPDATE Produto p set p.quantidade = p.quantidade - :quantidade WHERE p.id = :produtoId")
    public void alterarQuantidade(@Param("produtoId") Long produtoId, @Param("quantidade") Integer quantidade);


}
