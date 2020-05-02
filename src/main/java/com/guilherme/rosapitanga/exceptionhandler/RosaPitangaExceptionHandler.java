package com.guilherme.rosapitanga.exceptionhandler;

import com.guilherme.rosapitanga.exceptionhandler.exceptions.ErroAoRealizarUmaVenda;
import com.guilherme.rosapitanga.exceptionhandler.exceptions.UmOuMaisProdutosNaoForamEncontrados;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class RosaPitangaExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @NotNull
    @Override // Para atributos não identificados no JSON
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NotNull HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String mensagemUsuario = messageSource.getMessage("dados.incorretos", null, LocaleContextHolder.getLocale());

        String mensagemDesenvolvedor = ex.getCause().toString();

        List<Erro> errosDeLeitura = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

        return handleExceptionInternal(ex, errosDeLeitura, headers, status, request);
    }

    @NotNull
    @Override // Para erros de validação do Bean validation
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NotNull MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<Erro> errosBinding = criarListaDeErros(ex.getBindingResult());

        return handleExceptionInternal(ex, errosBinding, headers, status, request);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) { // Recurso não encontrado

        String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());

        String mensagemDesenvolvedor = ex.toString();

        List<Erro> ErroRecursoVazio = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

        return handleExceptionInternal(ex, ErroRecursoVazio, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request) { // Recursos vazios

        String mensagemUsuario = messageSource.getMessage("recurso.vazio", null, LocaleContextHolder.getLocale());

        String mensagemDesenvolvedor = ex.getCause().toString();

        List<Erro> ErroRecursoVazio = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

        return handleExceptionInternal(ex, ErroRecursoVazio, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({UmOuMaisProdutosNaoForamEncontrados.class})
    public ResponseEntity<Object> handleUmOuMaisProdutoNaoEncontrado(UmOuMaisProdutosNaoForamEncontrados ex, WebRequest request) { // Recursos vazios

        String mensagemDesenvolvedor = ex.getCause().toString();


        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private List<Erro> criarListaDeErros(@NotNull BindingResult bindingResult) { // Método responsável para buscar as mensagens de erros na validação

        List<Erro> erros = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {

            String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String mensagemDesenvolvedor = fieldError.toString();
            erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        }
        return erros;
    }

    public static class Erro { // Classe para facilitar as mensagens lançadas nos tratamentos de erros.

        private String mensagemUsuario;
        private String mensagemDesenvolvedor;

        public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
            this.mensagemUsuario = mensagemUsuario;
            this.mensagemDesenvolvedor = mensagemDesenvolvedor;
        }

        public String getMensagemUsuario() {
            return mensagemUsuario;
        }

        public String getMensagemDesenvolvedor() {
            return mensagemDesenvolvedor;
        }
    }
}
