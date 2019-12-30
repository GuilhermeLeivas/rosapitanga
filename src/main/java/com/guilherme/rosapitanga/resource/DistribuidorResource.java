package com.guilherme.rosapitanga.resource;

import com.guilherme.rosapitanga.model.Distribuidor;
import com.guilherme.rosapitanga.repository.filter.DistribuidorFilter;
import com.guilherme.rosapitanga.service.DistribuidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/distribuidores")
public class DistribuidorResource {

    @Autowired
    private DistribuidorService distribuidorService;

    @GetMapping
    public List<Distribuidor> buscarTodosDistribuidoresOuPesquisar(DistribuidorFilter distribuidorFilter) {

        return distribuidorService.buscarTodosOuPesquisar(distribuidorFilter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarDistribuidorPeloId(@PathVariable Long id) {

        return distribuidorService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> criarUmNovoDistribuidor(@Valid @RequestBody Distribuidor distribuidor, HttpServletResponse response) {

        return distribuidorService.criarNovo(distribuidor, response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarDistribuidor(@PathVariable Long id, @Valid @RequestBody Distribuidor distribuidor) {

        return distribuidorService.atualizarDistribuidor(id, distribuidor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarDistribuidorPeloId(@PathVariable Long id) {

        distribuidorService.deletarDistribuidor(id);
    }
}
