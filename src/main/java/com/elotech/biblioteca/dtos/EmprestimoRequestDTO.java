package com.elotech.biblioteca.dtos;

import com.elotech.biblioteca.enums.EmprestimoStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "Dados de criação da requisição de impréstimo")
public class EmprestimoRequestDTO {

    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;

    @NotNull(message = "ID do livro é obrigatório")
    private Long livroId;

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private EmprestimoStatus status;

    // Getters e Setters
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getLivroId() { return livroId; }
    public void setLivroId(Long livroId) { this.livroId = livroId; }

    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    public EmprestimoStatus getStatus() { return status; }
    public void setStatus(EmprestimoStatus status) { this.status = status; }
}