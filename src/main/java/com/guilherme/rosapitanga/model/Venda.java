package com.guilherme.rosapitanga.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.Nullable;
import org.hibernate.engine.profile.Fetch;
import org.springframework.data.repository.cdi.Eager;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataDeEfetuacao;

    private UUID codigoDaVenda;

    @Nullable
    @OneToOne(optional = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("vendas")
    private Crediario crediario;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FormaDePagamento formaDePagamento;

    @Transient
    private Carrinho carrinho;

    @Nullable
    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"categoria, distribuidor"})
    private List<Produto> produtos;

    private Double valorDaCompra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataDeEfetuacao() {
        return dataDeEfetuacao;
    }

    public void setDataDeEfetuacao(LocalDate dataDeEfetuacao) {
        this.dataDeEfetuacao = dataDeEfetuacao;
    }

    public UUID getCodigoDaVenda() {
        return codigoDaVenda;
    }

    public void setCodigoDaVenda(UUID codigoDaVenda) {
        this.codigoDaVenda = codigoDaVenda;
    }

    public Crediario getCrediario() {
        return crediario;
    }

    public void setCrediario(Crediario crediario) {
        this.crediario = crediario;
    }

    public FormaDePagamento getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Double getValorDaCompra() {
        return valorDaCompra;
    }

    public void setValorDaCompra(Double valorDaCompra) {
        this.valorDaCompra = valorDaCompra;
    }
}
