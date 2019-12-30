package com.guilherme.rosapitanga.service;

import com.guilherme.rosapitanga.event.RecursoCriadoEvent;
import com.guilherme.rosapitanga.model.Crediario;
import com.guilherme.rosapitanga.repository.CrediarioRepository;
import com.guilherme.rosapitanga.repository.filter.CrediarioFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class CrediarioService {

    @Autowired
    private CrediarioRepository crediarioRepository;

    @Autowired
    private ApplicationEventPublisher publisher;


    public Page<Crediario> buscarOuPesquisar(CrediarioFilter crediarioFilter, Pageable pageable) { //buscar todos ou filtrar pelo nome

        return crediarioRepository.filtrarCrediarios(crediarioFilter, pageable);
    }

    public ResponseEntity<Object> buscarPorId(Long id) { // Buscar por id o crediario

        Optional<Crediario> produto = crediarioRepository.findById(id);

        if (produto.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(produto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<Object> criarNovoCrediario(Crediario crediario, HttpServletResponse response) { // Criar novo crediario

        Crediario crediarioSalvo = crediarioRepository.save(crediario);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, crediarioSalvo.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(crediarioSalvo);
    }

    public ResponseEntity<?> atualizarCrediario(Long id, Crediario crediario) { // Atualizar crediario

        Crediario crediarioSalvo = crediarioRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
        BeanUtils.copyProperties(crediario, crediarioSalvo, "id");

        return ResponseEntity.ok(crediarioSalvo);
    }

    public void deletarCrediario(Long id) { // Deletar um crediario pelo seu id

        crediarioRepository.deleteById(id);
    }
}
