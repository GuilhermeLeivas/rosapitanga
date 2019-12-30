package com.guilherme.rosapitanga.event.listener;

import com.guilherme.rosapitanga.event.RecursoCriadoEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

    @Override
    public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) { // Criação do header Location

        HttpServletResponse response = recursoCriadoEvent.getResponse();
        Long id = recursoCriadoEvent.getId();

        adicionarHeaderLocation(response, id);


    }

    private void adicionarHeaderLocation(@NotNull HttpServletResponse response, Long id) { // Lógica para adicionar o header

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(id).toUri();

        response.setHeader("location", uri.toASCIIString());
    }
}
