package com.guilherme.rosapitanga.repository.categoria;

import com.guilherme.rosapitanga.model.Categoria;
import com.guilherme.rosapitanga.repository.filter.CategoriaFilter;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepositoryImpl implements CategoriaRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Categoria> filtrar(CategoriaFilter categoriaFilter) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Categoria> query = builder.createQuery(Categoria.class);

        Root<Categoria> root = query.from(Categoria.class);

        Predicate[] predicates = criarRestricoes(categoriaFilter, builder, root);

        query.where(predicates);

        TypedQuery<Categoria> queryPronta = manager.createQuery(query);

        return queryPronta.getResultList();
    }

    private Predicate[] criarRestricoes(CategoriaFilter categoriaFilter, CriteriaBuilder builder, Root<Categoria> root) {

        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(categoriaFilter.getNome())) { // Primeiro filtro
            predicates.add(builder.like(builder
                    .lower(root.get("nome")), "%" + categoriaFilter.getNome()
                    .toLowerCase() + "%"));

        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
