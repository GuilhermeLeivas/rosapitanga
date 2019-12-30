package com.guilherme.rosapitanga.repository.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ProdutoFilter {

    private String nomeProduto;

    private String codBarrasProduto;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataDe;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataAte;

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getCodBarrasProduto() {
        return codBarrasProduto;
    }

    public void setCodBarrasProduto(String codBarrasProduto) {
        this.codBarrasProduto = codBarrasProduto;
    }

    public LocalDate getDataDe() {
        return dataDe;
    }

    public void setDataDe(LocalDate dataDe) {
        this.dataDe = dataDe;
    }

    public LocalDate getDataAte() {
        return dataAte;
    }

    public void setDataAte(LocalDate dataAte) {
        this.dataAte = dataAte;
    }
}
