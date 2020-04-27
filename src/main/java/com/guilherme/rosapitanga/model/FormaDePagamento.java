package com.guilherme.rosapitanga.model;

import javax.persistence.Embeddable;

@Embeddable
public enum FormaDePagamento {

    DINHEIRO,
    CARTAO,
    CREDIARIO
}
