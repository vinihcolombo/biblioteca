package com.elotech.biblioteca.models;

import com.elotech.biblioteca.enums.LivroCategoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class LivroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "livro_seq")
    @SequenceGenerator(name = "livro_seq", sequenceName = "livro_sequence", allocationSize = 1)
    private Long id;

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    private String titulo;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String autor;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20, unique = true)
    @Pattern(
            regexp = "^(\\d{9}[\\dXx]|\\d{13})$",
            message = "ISBN inválido"
    )
    private String isbn;

    @NotNull
    @Column(nullable = false, name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private LivroCategoria categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public LivroCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(LivroCategoria categoria) {
        this.categoria = categoria;
    }
}