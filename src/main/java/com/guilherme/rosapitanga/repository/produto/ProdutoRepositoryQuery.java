package com.guilherme.rosapitanga.repository.produto;

import com.guilherme.rosapitanga.model.Produto;
import com.guilherme.rosapitanga.repository.filter.ProdutoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProdutoRepositoryQuery {

    public Page<Produto> filtrar(ProdutoFilter produtoFilter, Pageable pageable);
}
