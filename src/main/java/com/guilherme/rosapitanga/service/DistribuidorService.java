package com.guilherme.rosapitanga.service;

import com.guilherme.rosapitanga.event.RecursoCriadoEvent;
import com.guilherme.rosapitanga.exceptionhandler.exceptions.CNPJInvalidoException;
import com.guilherme.rosapitanga.model.Distribuidor;
import com.guilherme.rosapitanga.model.Produto;
import com.guilherme.rosapitanga.repository.DistribuidorRepository;
import com.guilherme.rosapitanga.repository.filter.DistribuidorFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.*;

@Service
public class DistribuidorService {

    @Autowired
    private DistribuidorRepository distribuidorRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private RestTemplate restTemplate;

    public List<Distribuidor> buscarTodosOuPesquisar(DistribuidorFilter distribuidorFilter) { // Busca todos

        return distribuidorRepository.filtrarDistribuidores(distribuidorFilter);
    }

    public ResponseEntity<Object> buscarPorId(Long id) { // Busca por Id

        Optional<Distribuidor> distribuidor = distribuidorRepository.findById(id);

        if (distribuidor.isPresent()) {
            return ResponseEntity.status(OK).body(distribuidor.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<Object> criarNovo(Distribuidor distribuidor, HttpServletResponse response) { // Criar novo distribuidor

        Distribuidor distribuidorSalvo = distribuidorRepository.save(distribuidor);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, distribuidorSalvo.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(distribuidorSalvo);
    }

    public ResponseEntity<?> atualizarDistribuidor(Long id, Distribuidor distribuidor) { //Atualizar distribuidor

        Distribuidor distribuidorSalvo = distribuidorRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        BeanUtils.copyProperties(distribuidor, distribuidorSalvo, "id");

        return ResponseEntity.ok(distribuidorSalvo);

    }

    public void deletarDistribuidor(Long id) { // Deletar distribuidor pelo id

        distribuidorRepository.deleteById(id);
    }
}
