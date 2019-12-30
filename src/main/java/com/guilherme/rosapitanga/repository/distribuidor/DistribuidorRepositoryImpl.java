package com.guilherme.rosapitanga.repository.distribuidor;

import com.guilherme.rosapitanga.model.Distribuidor;
import com.guilherme.rosapitanga.repository.filter.DistribuidorFilter;
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

public class DistribuidorRepositoryImpl implements DistribuidorRepositoryQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Distribuidor> filtrarDistribuidores(DistribuidorFilter distribuidorFilter) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Distribuidor> query = builder.createQuery(Distribuidor.class);

        Root<Distribuidor> root = query.from(Distribuidor.class);
        
        Predicate[] predicates = criarRestricoes(distribuidorFilter, builder, root);
        
        query.where(predicates);

        TypedQuery<Distribuidor> queryPronta = entityManager.createQuery(query);

        return queryPronta.getResultList();
    }

    private Predicate[] criarRestricoes(DistribuidorFilter distribuidorFilter, CriteriaBuilder builder, Root<Distribuidor> root) {

        List<Predicate> predicates = new ArrayList<>();

        if(!StringUtils.isEmpty(distribuidorFilter.getNomeEmpresa())) { // Primeiro filtro
            predicates.add(builder.like(builder
                                            .lower(root.get("nomeEmpresa")), "%" + distribuidorFilter.getNomeEmpresa()
                                            .toLowerCase() + "%"));

        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
