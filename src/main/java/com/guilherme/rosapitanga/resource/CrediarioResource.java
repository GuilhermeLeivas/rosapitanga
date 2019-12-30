package com.guilherme.rosapitanga.resource;

import com.guilherme.rosapitanga.model.Crediario;
import com.guilherme.rosapitanga.repository.filter.CrediarioFilter;
import com.guilherme.rosapitanga.service.CrediarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/crediarios")
public class CrediarioResource {

    @Autowired
    private CrediarioService crediarioService;

    @GetMapping
    private Page<Crediario> buscarTodosOuPesquisar(CrediarioFilter crediarioFilter, Pageable pageable) {

        return crediarioService.buscarOuPesquisar(crediarioFilter, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCrediarioPorId(@PathVariable Long id) {

        return crediarioService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> criarNovoCrediario(@Valid @RequestBody Crediario crediario, HttpServletResponse response) {

        return crediarioService.criarNovoCrediario(crediario, response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCrediario(@PathVariable Long id, @Valid @RequestBody Crediario crediario) {

        return crediarioService.atualizarCrediario(id, crediario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletarCrediario(@PathVariable Long id) {

         crediarioService.deletarCrediario(id);
    }
}
