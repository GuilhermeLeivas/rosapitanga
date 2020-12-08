package com.guilherme.rosapitanga.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.Nullable;

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

    @Column
    private LocalDate dataDeEfetuacao;

    @Column
    private UUID codigoDaVenda;

    @Nullable
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("vendas")
    @JoinColumn(name = "CREDIARIOID", referencedColumnName = "ID", nullable = true)
    private Crediario crediario;

    @NotNull
    @Enumerated
    @Column
    private FormaDePagamento formaDePagamento;

    @Nullable
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "venda_produto",
            joinColumns = @JoinColumn(name = "venda_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id"))
    @JsonIgnoreProperties(value = {"categoria, distribuidor"})
    private List<Produto> produtos;

    @Column
    private Double valorDaCompra;

    @Transient
    private Carrinho carrinho;

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

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }
}
