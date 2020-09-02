package com.guilherme.rosapitanga.dto;

import com.guilherme.rosapitanga.model.FormaDePagamento;

import java.time.LocalDate;

public class VendasEstatisticasMes {

    private FormaDePagamento formaDePagamento;
    private LocalDate mesReferente;
    private Double total;

    public VendasEstatisticasMes(FormaDePagamento formaDePagamento, LocalDate mesReferente, Double total) {
        this.formaDePagamento = formaDePagamento;
        this.mesReferente = mesReferente;
        this.total = total;
    }

    public FormaDePagamento getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    public LocalDate getMesReferente() {
        return mesReferente;
    }

    public void setMesReferente(LocalDate mesReferente) {
        this.mesReferente = mesReferente;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
