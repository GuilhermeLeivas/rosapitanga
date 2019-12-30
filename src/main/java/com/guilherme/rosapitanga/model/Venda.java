package com.guilherme.rosapitanga.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Date dataDeEfetuacao;

    private UUID codigoDaVenda;

    @OneToOne(optional = true)
    @JsonIgnoreProperties("vendas")
    private Crediario crediario;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FormaDePagamento formaDePagamento;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Produto> produtos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataDeEfetuacao() {
        return dataDeEfetuacao;
    }

    public void setDataDeEfetuacao(Date dataDeEfetuacao) {
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
}
