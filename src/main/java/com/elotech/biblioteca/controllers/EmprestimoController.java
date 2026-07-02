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

    public EmprestimoController(EmprestimoService emprestimoService){
        this.emprestimoService = emprestimoService;
    }

    private final EmprestimoService emprestimoService;

    @GetMapping
    public ResponseEntity<List<EmprestimoModel>> buscarTodosOsEmprestimos(){
        List<EmprestimoModel> request = emprestimoService.buscarTodosOsEmprestimos();
        return ResponseEntity.ok().body(request);
    }

    @PostMapping
    public ResponseEntity<EmprestimoModel> criarEmprestimo(@Valid @RequestBody EmprestimoModel emprestimoModel){
        EmprestimoModel request = emprestimoService.criarEmprestimo(emprestimoModel);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(request.getId())
                .toUri();

        return ResponseEntity.created(uri).body(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmprestimoPorId(@Valid @PathVariable Long id){
        emprestimoService.excluirEmprestimo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoModel> buscarEmprestimoPorId(@Valid @PathVariable Long id){
        EmprestimoModel emprestimo = emprestimoService.buscarEmprestimoPorId(id);
        return ResponseEntity.ok(emprestimo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmprestimoModel> atualizarEmprestimo(
            @Valid
            @PathVariable Long id,
            @RequestBody EmprestimoModel emprestimoModel)
    {
        return ResponseEntity.ok(emprestimoService.atualizarEmprestimo(id, emprestimoModel));
    }
    
}
