package com.guilherme.rosapitanga.exceptionhandler.exceptions;

public class CNPJInvalidoException extends IllegalArgumentException {

    @Override
    public String getMessage() {
        return "CNPJ invalido";
    }
}
