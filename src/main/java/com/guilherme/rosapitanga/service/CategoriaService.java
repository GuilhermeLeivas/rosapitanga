package com.guilherme.rosapitanga.service;

import com.guilherme.rosapitanga.event.RecursoCriadoEvent;
import com.guilherme.rosapitanga.model.Categoria;
import com.guilherme.rosapitanga.repository.CategoriaRepository;
import com.guilherme.rosapitanga.repository.filter.CategoriaFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    public List<Categoria> buscarTodasCategoriasOuPesquisar(CategoriaFilter categoriaFilter) { // Buscar todas categorias

        return categoriaRepository.filtrar(categoriaFilter);
    }

    public ResponseEntity<?> buscarPorId(Long id) { // Buscar categoria por id

        Optional<Categoria> categoriaSalva = categoriaRepository.findById(id);

        if (categoriaSalva.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(categoriaSalva.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<?> criarNovaCategoria(Categoria categoria, HttpServletResponse response) { // Criar nova categoria

        Categoria categoriaSalva = categoriaRepository.save(categoria);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    public ResponseEntity<Object> atualizarCategoria(Long id, Categoria categoria) {

        Categoria categoriaSalva = categoriaRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        BeanUtils.copyProperties(categoria, categoriaSalva, "id");

        return ResponseEntity.ok(categoriaSalva);
    }

    public void deletarCategoriaPorId(Long id) {

        categoriaRepository.deleteById(id);
    }
}
