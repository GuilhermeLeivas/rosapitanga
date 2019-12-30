package com.guilherme.rosapitanga.service;

import com.guilherme.rosapitanga.event.RecursoCriadoEvent;
import com.guilherme.rosapitanga.model.Produto;
import com.guilherme.rosapitanga.repository.ProdutoRepository;
import com.guilherme.rosapitanga.repository.filter.ProdutoFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    public Page<Produto> todosProdutosOuPesquisar(ProdutoFilter produtoFilter, Pageable pageable) { // Busca todos produtos salvos no banco de dados

        return produtoRepository.filtrar(produtoFilter, pageable);
    }

    public ResponseEntity<Object> buscarPorId(Long id) { // Busca por id

        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(produto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<Object> criarUmNovoProduto(Produto produto, HttpServletResponse response) { // Criação de um produto

        Produto produtoSalvo = produtoRepository.save(produto);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, produtoSalvo.getId()));

        return ResponseEntity.status(HttpStatus.OK).body(produtoSalvo);
    }

    public ResponseEntity<Object> atualizarProduto(Long id, Produto produto) { // Atualiza o produto

        Produto produtoSalvo = produtoRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
        BeanUtils.copyProperties(produto, produtoSalvo, "id");

        return ResponseEntity.ok(produtoSalvo);

    }

    public void deletarProdutoPeloId(Long id) {

        produtoRepository.deleteById(id);
    }
}