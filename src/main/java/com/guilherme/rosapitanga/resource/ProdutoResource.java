package com.guilherme.rosapitanga.resource;

import com.guilherme.rosapitanga.model.Produto;
import com.guilherme.rosapitanga.repository.filter.ProdutoFilter;
import com.guilherme.rosapitanga.service.ProdutoService;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public Page<Produto> buscarTodosProdutosOuPesquisar(@Nullable ProdutoFilter produtoFilter, Pageable pageable) {

        return produtoService.todosProdutosOuPesquisar(produtoFilter, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProdutoPeloId(@PathVariable Long id) {

        return produtoService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> criarProduto(@Valid @RequestBody Produto produto, HttpServletResponse response) {

        return produtoService.criarUmNovoProduto(produto, response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProduto(@PathVariable Long id, @Valid @RequestBody Produto produto) {

        return produtoService.atualizarProduto(id, produto);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarProduto(@PathVariable Long id) {

        produtoService.deletarProdutoPeloId(id);
    }

}
