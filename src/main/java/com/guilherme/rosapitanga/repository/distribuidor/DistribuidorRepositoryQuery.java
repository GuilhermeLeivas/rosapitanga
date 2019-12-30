package com.guilherme.rosapitanga.repository.distribuidor;

import com.guilherme.rosapitanga.model.Distribuidor;
import com.guilherme.rosapitanga.repository.filter.DistribuidorFilter;

import java.util.List;

public interface DistribuidorRepositoryQuery {

    public List<Distribuidor> filtrarDistribuidores(DistribuidorFilter distribuidorFilter);
}
