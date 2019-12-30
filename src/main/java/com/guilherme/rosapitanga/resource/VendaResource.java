package com.guilherme.rosapitanga.resource;

import com.guilherme.rosapitanga.model.Venda;
import com.guilherme.rosapitanga.repository.filter.VendaFilter;
import com.guilherme.rosapitanga.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/vendas")
public class VendaResource {

    @Autowired
    private VendaService vendaService;

    @GetMapping
    private Page<Venda> buscarTodosOuPesquisarVendas(VendaFilter vendaFilter, Pageable pageable) {

        return vendaService.buscarTodosOuPesquisar(vendaFilter, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarVendaPorId(@PathVariable Long id) {

        return vendaService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> criarNovaVenda(@Valid @RequestBody Venda venda, HttpServletResponse response) {

        return vendaService.criarNovaVenda(venda, response);
    }
}
