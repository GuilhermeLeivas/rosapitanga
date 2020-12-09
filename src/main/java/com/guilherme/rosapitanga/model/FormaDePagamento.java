package com.guilherme.rosapitanga.model;

public enum FormaDePagamento {

    DINHEIRO,
    CARTAO,
    CREDIARIO;

    public String getDescricao() {
        String s = "";
        switch (this) {
            case DINHEIRO:
                s = "Dinheiro";
                break;
            case CARTAO:
                s = "Cartão";
                break;
            case CREDIARIO:
                s = "Crediário";
                break;
        }
        return s;
    }
}
