package com.guilherme.rosapitanga.repository.projection;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ResumoVenda {

    private Long id;
    private Date dataDeEfetuacao;
    private UUID codigoDaVenda;
    private String crediario;
    private String formaDePagamento;
    private List<String> produtos;

    public ResumoVenda(Long id, Date dataDeEfetuacao, UUID codigoDaVenda, String crediario, String formaDePagamento, List<String> produtos) {

        this.id = id;
        this.dataDeEfetuacao = dataDeEfetuacao;
        this.codigoDaVenda = codigoDaVenda;
        this.crediario = crediario;
        this.formaDePagamento = formaDePagamento;
        this.produtos = produtos;
    }

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

    public String getCrediario() {
        return crediario;
    }

    public void setCrediario(String crediario) {
        this.crediario = crediario;
    }

    public String getFormaPagamento() {
        return formaDePagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaDePagamento = formaPagamento;
    }

    public List<String> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<String> produtos) {
        this.produtos = produtos;
    }
}
