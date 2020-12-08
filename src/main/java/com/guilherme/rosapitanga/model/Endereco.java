package com.guilherme.rosapitanga.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ENDERECO")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MUNICIPIOID", referencedColumnName = "ID")
    private Municipio cidade;

    @Column
    private String bairro;
    @Column
    private String avenida;
    @Column
    private Long numero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Municipio getCidade() {
        return cidade;
    }

    public void setCidade(Municipio cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getAvenida() {
        return avenida;
    }

    public void setAvenida(String avenida) {
        this.avenida = avenida;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return id.equals(endereco.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
