package com.elotech.biblioteca.controllers;

import com.elotech.biblioteca.dtos.LivroDTO;
import com.elotech.biblioteca.models.LivroModel;
import com.elotech.biblioteca.services.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private LivroDTO toDTO(LivroModel livro){
        if (livro == null) return null;

        LivroDTO dto = new LivroDTO();
        dto.setId(livro.getId());
        dto.setTitulo(livro.getTitulo());
        dto.setAutor(livro.getAutor());
        dto.setIsbn(livro.getIsbn());
        dto.setDataPublicacao(livro.getDataPublicacao());
        dto.setCategoria(livro.getCategoria());
        return dto;
    }

    private LivroModel toEntity(LivroDTO dto){
        if (dto == null) return null;

        LivroModel livro = new LivroModel();
        livro.setId(dto.getId());
        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setIsbn(dto.getIsbn());
        livro.setDataPublicacao(dto.getDataPublicacao());
        livro.setCategoria(dto.getCategoria());
        return livro;
    }

    private void updateEntity(LivroModel livro, LivroDTO dto){
        if (dto.getTitulo() != null) {
            livro.setTitulo(dto.getTitulo());
        }
        if (dto.getAutor() != null) {
            livro.setAutor(dto.getAutor());
        }
        if (dto.getIsbn() != null) {
            livro.setIsbn(dto.getIsbn());
        }
        if (dto.getDataPublicacao() != null) {
            livro.setDataPublicacao(dto.getDataPublicacao());
        }
        if (dto.getCategoria() != null) {
            livro.setCategoria(dto.getCategoria());
        }
    }

    public LivroController(LivroService livroService){
        this.livroService = livroService;
    }

    private final LivroService livroService;

    @Operation(summary = "Lista todos os livros")
    @ApiResponse(responseCode = "200", description = "Livros listados com sucesso")
    @GetMapping
    public ResponseEntity<List<LivroDTO>> buscarTodosOsLivros(){
        List<LivroModel> request = livroService.buscarTodosOsLivros();

        List<LivroDTO> dtos = request.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Cria um novo livro")
    @ApiResponse(responseCode = "201", description = "Livro criado com sucesso")
    @PostMapping
    public ResponseEntity<LivroDTO> criarLivro(@Valid @RequestBody LivroDTO livroDTO){

        LivroModel livro = toEntity(livroDTO);

        LivroModel request = livroService.criarLivro(livro);

        LivroDTO responseDTO = toDTO(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(request.getId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @Operation(summary = "Deleta um livro da tabela")
    @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivroPorId(@PathVariable Long id){
        livroService.excluirLivro(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca um único livro")
    @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso")
    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscarLivroPorId(@PathVariable Long id){
        try {
        LivroModel livro = livroService.buscarLivroPorId(id);
        LivroDTO dto = toDTO(livro);
        return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Atualiza as informações de um livro")
    @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> atualizarLivro(
            @PathVariable Long id,
            @RequestBody LivroDTO livroDTO)
    {
        LivroModel livro = livroService.buscarLivroPorId(id);

        updateEntity(livro, livroDTO);

        LivroModel updated = livroService.atualizarLivro(id, livro);

        return ResponseEntity.ok(toDTO(updated));
    }
}
