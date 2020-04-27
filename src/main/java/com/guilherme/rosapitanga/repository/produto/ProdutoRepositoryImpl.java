package com.guilherme.rosapitanga.repository.produto;

import com.guilherme.rosapitanga.model.Produto;
import com.guilherme.rosapitanga.repository.filter.ProdutoFilter;
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

public class ProdutoRepositoryImpl implements ProdutoRepositoryQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Produto> filtrar(ProdutoFilter produtoFilter, Pageable pageable) { // Query em si

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);

        Root<Produto> root = criteriaQuery.from(Produto.class);

        Predicate[] predicates = criarRestricoes(produtoFilter, criteriaBuilder, root);

        criteriaQuery.where(predicates);

        TypedQuery<Produto> query = entityManager.createQuery(criteriaQuery);

        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(produtoFilter));
    }

    private Predicate[] criarRestricoes(ProdutoFilter produtoFilter,
                                        CriteriaBuilder criteriaBuilder, Root<Produto> root) { // Filtros

        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(produtoFilter.getNomeProduto())) { // Primeiro filtro
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder
                            .lower(root.get("nome")), "%" + produtoFilter.getNomeProduto()
                            .toLowerCase() + "%"
            ));

        }

        if (!StringUtils.isEmpty(produtoFilter.getCodBarrasProduto())) { // Segundo filtro
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder
                            .lower(root.get("codBarras")), "%" + produtoFilter.getCodBarrasProduto()
                            .toLowerCase() + "%"
            ));
        }

        if (produtoFilter.getDataDe() != null) { // Terceiro filtro
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("dataEntrada"), produtoFilter.getDataDe()
            ));

        }

        if (produtoFilter.getDataAte() != null) { // Quarto filtro
            predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("dataEntrada"), produtoFilter.getDataAte()
            ));

        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<Produto> query, Pageable pageable) {// Restricoes de paginacao

        int pagAtual = pageable.getPageNumber();
        int totalDeRegistrosPorPag = pageable.getPageSize();
        int primeiroRegistroPag = pagAtual * totalDeRegistrosPorPag;

        query.setFirstResult(primeiroRegistroPag);
        query.setMaxResults(totalDeRegistrosPorPag);

    }

    private Long total(ProdutoFilter produtoFilter) { // Definindo a quantidade de resultados na query

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        Predicate[] predicates = criarRestricoes(produtoFilter, builder, root);

        criteriaQuery.where(predicates);

        criteriaQuery.select(builder.count(root));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}
