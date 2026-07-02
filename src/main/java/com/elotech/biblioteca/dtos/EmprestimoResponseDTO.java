package com.elotech.biblioteca.dtos;

import com.elotech.biblioteca.enums.EmprestimoStatus;
import java.time.LocalDate;

public class EmprestimoResponseDTO {

    private Long id;
    private UsuarioDTO usuario;
    private LivroDTO livro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private EmprestimoStatus status;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UsuarioDTO getUsuario() { return usuario; }
    public void setUsuario(UsuarioDTO usuario) { this.usuario = usuario; }

    public LivroDTO getLivro() { return livro; }
    public void setLivro(LivroDTO livro) { this.livro = livro; }

    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    public EmprestimoStatus getStatus() { return status; }
    public void setStatus(EmprestimoStatus status) { this.status = status; }
}