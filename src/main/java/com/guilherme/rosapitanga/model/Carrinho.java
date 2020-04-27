package com.guilherme.rosapitanga.model;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class Carrinho {

    private List<ItemVenda> itensDaVenda;

    public List<ItemVenda> getItensDaVenda() {
        return itensDaVenda;
    }

    public void setItensDaVenda(List<ItemVenda> itensDaVenda) {
        this.itensDaVenda = itensDaVenda;
    }
}
