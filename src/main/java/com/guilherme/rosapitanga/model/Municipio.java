package com.guilherme.rosapitanga.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MUNICIPIO")
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String nome;

    @Column
    @NotNull
    private String UF;

    @Column
    @NotNull
    private String cep;
}
