package com.guilherme.rosapitanga.repository.venda;

import com.guilherme.rosapitanga.model.Venda;
import com.guilherme.rosapitanga.repository.filter.VendaFilter;
import com.guilherme.rosapitanga.repository.projection.ResumoVenda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VendaRepositoryQuery {

    public Page<Venda> filtrarEPaginarVendas(VendaFilter vendaFilter, Pageable pageable);

    public Page<ResumoVenda> resumirVenda(VendaFilter vendaFilter, Pageable pageable);
}
