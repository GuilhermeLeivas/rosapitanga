package com.guilherme.rosapitanga.model;

import javax.persistence.Embeddable;

@Embeddable
public class Endereco {

    private String cidade;

    private String bairro;

    private String avenida;

    private Long numero;

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getAvenida() {
        return avenida;
    }

    public void setAvenida(String avenida) {
        this.avenida = avenida;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }
}
