package com.guilherme.rosapitanga.repository.crediario;

import com.guilherme.rosapitanga.model.Crediario;
import com.guilherme.rosapitanga.repository.filter.CrediarioFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

public class CrediarioRepositoryImpl implements CrediarioRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Crediario> filtrarCrediarios(CrediarioFilter crediarioFilter, Pageable pageable) {

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Crediario> criteriaQuery = criteriaBuilder.createQuery(Crediario.class);

        Root<Crediario> root = criteriaQuery.from(Crediario.class);

        Predicate[] predicates = criarRestricoes(crediarioFilter, criteriaBuilder, root);

        criteriaQuery.where(predicates);

        TypedQuery<Crediario> query = manager.createQuery(criteriaQuery);

        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(crediarioFilter)) ;


    }

    private Predicate[] criarRestricoes(CrediarioFilter crediarioFilter,
                                        CriteriaBuilder criteriaBuilder, Root<Crediario> root) { // Filtros

        List<Predicate> predicates = new ArrayList<>();

        if(!StringUtils.isEmpty(crediarioFilter.getNome())) { // Primeiro filtro
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder
                            .lower(root.get("nome")), "%" + crediarioFilter.getNome()
                            .toLowerCase() + "%"
            ));

        }
        return predicates.toArray(new Predicate[predicates.size()]);
}

    private void adicionarRestricoesDePaginacao(TypedQuery<Crediario> query, Pageable pageable) {// Restricoes de paginacao

        int pagAtual = pageable.getPageNumber();
        int totalDeRegistrosPorPag = pageable.getPageSize();
        int primeiroRegistroPag = pagAtual * totalDeRegistrosPorPag;

        query.setFirstResult(primeiroRegistroPag);
        query.setMaxResults(totalDeRegistrosPorPag);

    }

    private Long total(CrediarioFilter crediarioFilter) { // Definindo a quantidade de resultados na query

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Crediario> root = criteriaQuery.from(Crediario.class);

        Predicate[] predicates = criarRestricoes(crediarioFilter, builder, root);

        criteriaQuery.where(predicates);

        criteriaQuery.select(builder.count(root));

        return manager.createQuery(criteriaQuery).getSingleResult();
    }


}
