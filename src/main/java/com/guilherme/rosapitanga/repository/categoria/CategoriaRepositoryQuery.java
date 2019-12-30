package com.guilherme.rosapitanga.repository.categoria;

import com.guilherme.rosapitanga.model.Categoria;
import com.guilherme.rosapitanga.repository.filter.CategoriaFilter;

import java.util.List;

public interface CategoriaRepositoryQuery {

    public List<Categoria> filtrar(CategoriaFilter categoriaFilter);
}
