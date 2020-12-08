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
import java.util.stream.Collectors;

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

        montaNovaVenda(produtosDaVenda, venda);
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

    private void montaNovaVenda(List<ItemVenda> produtosDaVenda, Venda venda) { // Verifica se os produtos na venda existem

        try {
            List<Produto> produtosAchadosEVerificados = verificaProdutosDaVenda(produtosDaVenda);

            venda.setProdutos(produtosAchadosEVerificados);
            venda.setValorDaCompra(totalDaVenda(produtosDaVenda));
            venda.setDataDeEfetuacao(LocalDate.now());
            venda.setCodigoDaVenda(UUID.randomUUID());

        } catch (ErroAoRealizarUmaVenda ex) {
            ex.getMessage();
        }
    }

    private List<Produto> verificaProdutosDaVenda(List<ItemVenda> produtosDaVenda) {
        //TODO: Fazer com que mesmo se não achar UM produto, os outros sejam trazidos

        List<Long> ids = produtosDaVenda.stream()
                .map(item -> item.getProduto().getId())
                .collect(Collectors.toList());

        return produtoRepository.acharProdutosPorListaDeIds(ids)
                                    .orElseThrow(() -> new UmOuMaisProdutosNaoForamEncontrados(1));
    }

    private Double totalDaVenda(List<ItemVenda> itensVenda) {
        Double valorDaVenda = 0.0;
       for(ItemVenda item : itensVenda) {
           Optional<Produto> produtoItem = produtoRepository.findById(item.getProduto().getId());
           if(produtoItem.isPresent()) {
               valorDaVenda += produtoItem.get().getPrecoVenda() * item.getQuantidade();
           }
       }
        return valorDaVenda;
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
