package com.guilherme.rosapitanga.service;

import com.guilherme.rosapitanga.event.RecursoCriadoEvent;
import com.guilherme.rosapitanga.model.Crediario;
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
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<?> criarNovaVenda(Venda venda, HttpServletResponse response) { // Criação da venda

        List<Produto> produtosDaVenda = venda.getProdutos();

        verificarProdutosNaVenda(produtosDaVenda);

        if(venda.getCrediario() != null) {

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

    private void verificarProdutosNaVenda(List<Produto> produtosDaVenda) { // Verifica se os produtos na venda existem

        for (Produto produto : produtosDaVenda) {

            produtoRepository.findById(produto.getId())
                    .orElseThrow(() -> new EmptyResultDataAccessException(1));

        }
    }
}
