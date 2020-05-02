package com.guilherme.rosapitanga.repository;

import com.guilherme.rosapitanga.model.Produto;
import com.guilherme.rosapitanga.repository.produto.ProdutoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQuery {

    @Transactional
    @Modifying
    @Query("UPDATE Produto p set p.quantidade = p.quantidade - :quantidade WHERE p.id = :produtoId")
    public void alterarQuantidade(@Param("produtoId") Long produtoId, @Param("quantidade") Integer quantidade);

    @Query(value = "select * from produto where id in :ids", nativeQuery = true)
    public Optional<List<Produto>> acharProdutosPorListaDeIds(@Param("ids") List<Long> ids);


}
