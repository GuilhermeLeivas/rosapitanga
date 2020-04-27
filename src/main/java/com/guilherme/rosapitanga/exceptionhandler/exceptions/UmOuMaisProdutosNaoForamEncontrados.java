package com.guilherme.rosapitanga.exceptionhandler.exceptions;

import org.springframework.dao.IncorrectResultSizeDataAccessException;

public class UmOuMaisProdutosNaoForamEncontrados extends IncorrectResultSizeDataAccessException {

    public UmOuMaisProdutosNaoForamEncontrados(int expectedSize) {
        super(expectedSize);
    }
}
