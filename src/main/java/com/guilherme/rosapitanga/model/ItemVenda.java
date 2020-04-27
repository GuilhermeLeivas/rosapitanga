package com.guilherme.rosapitanga.model;

import javax.persistence.*;
import javax.transaction.Transactional;

@Transactional
public class ItemVenda {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long produtoId;
    private Integer quantidade;

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
