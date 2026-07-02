package com.elotech.biblioteca.controllers;

import com.elotech.biblioteca.models.LivroModel;
import com.elotech.biblioteca.services.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    public LivroController(LivroService livroService){
        this.livroService = livroService;
    }

    private final LivroService livroService;

    @GetMapping
    public ResponseEntity<List<LivroModel>> buscarTodosOsLivros(){
        List<LivroModel> request = livroService.buscarTodosOsLivros();
        return ResponseEntity.ok().body(request);
    }

    @PostMapping
    public ResponseEntity<LivroModel> criarLivro(@Valid @RequestBody LivroModel livroModel){
        LivroModel request = livroService.criarLivro(livroModel);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(request.getId())
                .toUri();

        return ResponseEntity.created(uri).body(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivroPorId(@PathVariable Long id){
        livroService.excluirLivro(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroModel> buscarLivroPorId(@PathVariable Long id){
        try {
        LivroModel livro = livroService.buscarLivroPorId(id);
        return ResponseEntity.ok(livro);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroModel> atualizarLivro(
            @PathVariable Long id,
            @RequestBody LivroModel livroModel)
    {
        return ResponseEntity.ok(livroService.atualizarLivro(id, livroModel));
    }
}
