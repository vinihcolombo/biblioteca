package com.elotech.biblioteca.models;

import com.elotech.biblioteca.validations.DataNaoFutura;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;

@Entity
public class UsuarioModel {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @DataNaoFutura
    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    @Pattern(
            regexp = "^[0-9()+\\-\\s]{8,20}$",
            message = "Telefone inválido"
    )
    private String telefone;
}