package com.guilherme.rosapitanga.exceptionhandler.exceptions;

import org.springframework.dao.IncorrectResultSizeDataAccessException;

public class UmOuMaisProdutosNaoForamEncontrados extends IncorrectResultSizeDataAccessException {

    public UmOuMaisProdutosNaoForamEncontrados(int expectedSize) {
        super(expectedSize);
    }

    @Override
    public String getMessage() {
        return "Um produto ou mais não foram encontrados, por favor, tente novamente";
    }
}
