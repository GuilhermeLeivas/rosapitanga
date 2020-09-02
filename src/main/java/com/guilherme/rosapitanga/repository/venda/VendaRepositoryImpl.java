package com.guilherme.rosapitanga.repository.venda;

import com.guilherme.rosapitanga.dto.VendasEstatisticasMes;
import com.guilherme.rosapitanga.model.Venda;
import com.guilherme.rosapitanga.repository.filter.VendaFilter;
import com.guilherme.rosapitanga.repository.projection.ResumoVenda;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VendaRepositoryImpl implements VendaRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendasEstatisticasMes> porMes(LocalDate mesReferente) { // Dados estatísticos por mês das vendas
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<VendasEstatisticasMes> query = builder.createQuery(VendasEstatisticasMes.class);
        Root<Venda> root = query.from(Venda.class);

        query.select(builder.construct(VendasEstatisticasMes.class,
                                                        root.get("formaPagamento"),
                                                        builder.sum(root.get("valorDaCompra"))));

        LocalDate primeiroDia = mesReferente.withDayOfMonth(1);
        LocalDate ultimoDia = mesReferente.withDayOfMonth(mesReferente.lengthOfMonth());

        query.where(
                builder.greaterThanOrEqualTo(root.get("DataDeEfetuacao"), primeiroDia),
                builder.lessThanOrEqualTo(root.get("DataDeEfetuacao"), ultimoDia)
        );

        query.groupBy(root.get("formaPagamento"),
                      root.get("dataDeEfetuacao"));

        TypedQuery<VendasEstatisticasMes> typedQuery = manager.createQuery(query);

        return typedQuery.getResultList();
    }

    @Override
    public Page<Venda> filtrarEPaginarVendas(VendaFilter vendaFilter, Pageable pageable) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Venda> query = builder.createQuery(Venda.class);

        Root<Venda> root = query.from(Venda.class);

        Predicate[] predicates = criarRestricoes(vendaFilter, builder, root);

        query.where(predicates);

        TypedQuery<Venda> queryPronta = manager.createQuery(query);

        adicionarRestricoesDePaginacao(queryPronta, pageable);

        return new PageImpl<>(queryPronta.getResultList(), pageable, total(vendaFilter));
    }

    @Override
    public Page<ResumoVenda> resumirVenda(VendaFilter vendaFilter, Pageable pageable) { // Resumir vendas

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<ResumoVenda> query = builder.createQuery(ResumoVenda.class);
        Root<Venda> root = query.from(Venda.class);

        query.select(builder.construct(ResumoVenda.class,

                root.get("id"), root.get("dataDeEfetuacao"), root.get("codigoDaVenda"),
                root.get("crediario").get("nome"), root.get("formaDePagamento"),
                root.get("produtos").get("nome").get("precoVenda")
        ));

        Predicate[] predicates = criarRestricoes(vendaFilter, builder, root);

        query.where(predicates);

        TypedQuery<ResumoVenda> queryPronta = manager.createQuery(query);

        adicionarRestricoesDePaginacao(queryPronta, pageable);

        return new PageImpl<>(queryPronta.getResultList(), pageable, total(vendaFilter));
    }

    private Predicate[] criarRestricoes(VendaFilter vendaFilter, CriteriaBuilder builder, Root<Venda> root) {

        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(vendaFilter.getCodigoDaVenda())) {

            predicates.add(builder.like(
                    builder
                            .lower(root.get("codigoVenda")), "%" + vendaFilter.getCodigoDaVenda()
                            .toLowerCase() + "%"));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {// Restricoes de paginacao

        int pagAtual = pageable.getPageNumber();
        int totalDeRegistrosPorPag = pageable.getPageSize();
        int primeiroRegistroPag = pagAtual * totalDeRegistrosPorPag;

        query.setFirstResult(primeiroRegistroPag);
        query.setMaxResults(totalDeRegistrosPorPag);

    }

    private Long total(VendaFilter vendaFilter) { // Definindo a quantidade de resultados na query

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Venda> root = criteriaQuery.from(Venda.class);

        Predicate[] predicates = criarRestricoes(vendaFilter, builder, root);

        criteriaQuery.where(predicates);

        criteriaQuery.select(builder.count(root));

        return manager.createQuery(criteriaQuery).getSingleResult();
    }
}
