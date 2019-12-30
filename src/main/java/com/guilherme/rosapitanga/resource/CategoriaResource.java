package com.guilherme.rosapitanga.resource;

import com.guilherme.rosapitanga.model.Categoria;
import com.guilherme.rosapitanga.repository.filter.CategoriaFilter;
import com.guilherme.rosapitanga.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> buscarTodasCategoriasOuPesquisar(CategoriaFilter categoriaFilter) {

        return categoriaService.buscarTodasCategoriasOuPesquisar(categoriaFilter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCategoriaPorId(@PathVariable Long id) {

        return categoriaService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> criarNovaCategoria(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {

        return categoriaService.criarNovaCategoria(categoria, response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> criarNovaCategoria(@PathVariable Long id, @Valid @RequestBody Categoria categoria) {

        return categoriaService.atualizarCategoria(id, categoria);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCategoria(@PathVariable Long id) {

        categoriaService.deletarCategoriaPorId(id);
    }
}
