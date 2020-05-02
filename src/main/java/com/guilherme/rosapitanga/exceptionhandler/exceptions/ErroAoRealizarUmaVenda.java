package com.guilherme.rosapitanga.exceptionhandler.exceptions;

public class ErroAoRealizarUmaVenda extends RuntimeException {

    @Override
    public String getMessage() {
        return "Houve um problema ao processar a venda, por favor verifique os produtos e tente novamente";
    }
}
