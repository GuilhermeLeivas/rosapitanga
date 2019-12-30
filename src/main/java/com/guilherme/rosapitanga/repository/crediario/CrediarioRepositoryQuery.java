package com.guilherme.rosapitanga.repository.crediario;

import com.guilherme.rosapitanga.model.Crediario;
import com.guilherme.rosapitanga.repository.filter.CrediarioFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrediarioRepositoryQuery {

    public Page<Crediario> filtrarCrediarios(CrediarioFilter crediarioFilter, Pageable pageable);
}
