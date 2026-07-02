package com.elotech.biblioteca.controllers;

import com.elotech.biblioteca.models.EmprestimoModel;
import com.elotech.biblioteca.services.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<EmprestimoModel> criarEmprestimo(
            @Valid @RequestBody EmprestimoModel emprestimoModel) {  // ← ENTIDADE, não DTO!

        EmprestimoModel request = emprestimoService.criarEmprestimo(emprestimoModel);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(request.getId())
                .toUri();

        return ResponseEntity.created(uri).body(request);
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoModel>> buscarTodosOsEmprestimos() {
        List<EmprestimoModel> request = emprestimoService.buscarTodosOsEmprestimos();
        return ResponseEntity.ok(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoModel> buscarEmprestimoPorId(@PathVariable Long id) {
        EmprestimoModel emprestimo = emprestimoService.buscarEmprestimoPorId(id);
        return ResponseEntity.ok(emprestimo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmprestimoModel> atualizarEmprestimo(
            @PathVariable Long id,
            @RequestBody EmprestimoModel emprestimoModel) {
        return ResponseEntity.ok(emprestimoService.atualizarEmprestimo(id, emprestimoModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmprestimoPorId(@PathVariable Long id) {
        emprestimoService.excluirEmprestimo(id);
        return ResponseEntity.noContent().build();
    }
}