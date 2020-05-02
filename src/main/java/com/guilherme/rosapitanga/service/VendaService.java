package com.guilherme.rosapitanga.service;

import com.guilherme.rosapitanga.event.RecursoCriadoEvent;
import com.guilherme.rosapitanga.exceptionhandler.exceptions.ErroAoRealizarUmaVenda;
import com.guilherme.rosapitanga.exceptionhandler.exceptions.FailedToUpdateResourceException;
import com.guilherme.rosapitanga.exceptionhandler.exceptions.UmOuMaisProdutosNaoForamEncontrados;
import com.guilherme.rosapitanga.model.ItemVenda;
import com.guilherme.rosapitanga.model.Produto;
import com.guilherme.rosapitanga.model.Venda;
import com.guilherme.rosapitanga.repository.CrediarioRepository;
import com.guilherme.rosapitanga.repository.ProdutoRepository;
import com.guilherme.rosapitanga.repository.VendaRepository;
import com.guilherme.rosapitanga.repository.filter.VendaFilter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CrediarioRepository crediarioRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    public Page<Venda> buscarTodosOuPesquisar(VendaFilter vendaFilter, Pageable pageable) {

        return vendaRepository.filtrarEPaginarVendas(vendaFilter, pageable);
    }

    public ResponseEntity<?> buscarPorId(Long id) {

        Optional<Venda> vendaSalva = vendaRepository.findById(id);

        if (vendaSalva.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(vendaSalva.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Async
    public ResponseEntity<?> criarNovaVenda(Venda venda, HttpServletResponse response) { // Criação da venda

        List<ItemVenda> produtosDaVenda = venda.getCarrinho().getItensDaVenda();

        verificarProdutosNoCarrinhoEAdicionarNaVenda(produtosDaVenda, venda);
        alterandoAQuantidadeDosProdutos(produtosDaVenda);

        if (venda.getCrediario() != null) {

            verificarCrediario(venda);
        }

        Venda vendaSalva = vendaRepository.save(venda);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, vendaSalva.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalva);
    }

    private void verificarCrediario(Venda venda) { // Se tiver crediario na venda, aqui verificamos sua existência

        crediarioRepository.findById(venda.getCrediario().getId())
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

    }

    private void verificarProdutosNoCarrinhoEAdicionarNaVenda(List<ItemVenda> produtosDaVenda, Venda venda) { // Verifica se os produtos na venda existem

        try {
            List<Produto> produtosAchadosEVerificados = procurarProdutosDentroDosItensDaVenda(produtosDaVenda);

            venda.setProdutos(produtosAchadosEVerificados);
            venda.setValorDaCompra(totalDaVenda(produtosAchadosEVerificados, produtosDaVenda));
            venda.setDataDeEfetuacao(LocalDate.now());
            venda.setCodigoDaVenda(UUID.randomUUID());

        } catch (ErroAoRealizarUmaVenda ex) {
            ex.getMessage();
        }
    }

    private List<Produto> procurarProdutosDentroDosItensDaVenda(List<ItemVenda> produtosDaVenda) {

        List<Long> ids = produtosDaVenda.stream()
                .map(item -> item.getProduto().getId())
                .collect(Collectors.toList());

        return produtoRepository.acharProdutosPorListaDeIds(ids)
                                    .orElseThrow(() -> new UmOuMaisProdutosNaoForamEncontrados(1));
    }

    @NotNull
    private Double totalDaVenda(List<Produto> produtos, List<ItemVenda> itensParaPegarQuantidade) {

        BiFunction<ItemVenda, Produto, Double> somarValor = (itemVenda, produto) -> (itemVenda.getQuantidade() * produto.getPrecoVenda());
        Double valorDaVenda = produtos.stream().mapToDouble(produto ->
                                itensParaPegarQuantidade.stream().mapToDouble(itemVenda -> somarValor.apply(itemVenda, produto)).sum()).sum();

        return valorDaVenda / 2;
    }

    private void alterandoAQuantidadeDosProdutos(List<ItemVenda> produtosDaVenda) {

        try {
            produtosDaVenda.stream()
                       .forEach(produtoNaLista -> produtoRepository.alterarQuantidade(produtoNaLista.getProduto().getId(),
                                                    produtoNaLista.getQuantidade()));
        } catch (FailedToUpdateResourceException e) {
            e.printStackTrace();
        }
    }
}
