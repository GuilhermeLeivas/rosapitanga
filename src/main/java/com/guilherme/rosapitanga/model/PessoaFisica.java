package com.guilherme.rosapitanga.model;

import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "PESSOAFISICA")
public class PessoaFisica extends Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CPF
    @Column
    @NotNull
    private String cpf;

    @Column
    @NotNull
    private String RG;

    @Column
    @NotNull
    private LocalDate dtEmissaoRG;

    @Column
    @NotNull
    private String orgaoEmissorRG;

    @Column
    private String nomePai;

    @Column
    private String nomeMae;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MUNICIPIOID", referencedColumnName = "ID")
    @NotNull
    private Municipio naturalidade;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String RG) {
        this.RG = RG;
    }

    public LocalDate getDtEmissaoRG() {
        return dtEmissaoRG;
    }

    public void setDtEmissaoRG(LocalDate dtEmissaoRG) {
        this.dtEmissaoRG = dtEmissaoRG;
    }

    public String getOrgaoEmissorRG() {
        return orgaoEmissorRG;
    }

    public void setOrgaoEmissorRG(String orgaoEmissorRG) {
        this.orgaoEmissorRG = orgaoEmissorRG;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public Municipio getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(Municipio naturalidade) {
        this.naturalidade = naturalidade;
    }
}
