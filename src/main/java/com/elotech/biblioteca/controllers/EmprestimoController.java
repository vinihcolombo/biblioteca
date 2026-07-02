package com.elotech.biblioteca.controllers;

import com.elotech.biblioteca.dtos.EmprestimoRequestDTO;
import com.elotech.biblioteca.dtos.EmprestimoResponseDTO;
import com.elotech.biblioteca.dtos.LivroDTO;
import com.elotech.biblioteca.dtos.UsuarioDTO;
import com.elotech.biblioteca.enums.EmprestimoStatus;
import com.elotech.biblioteca.models.EmprestimoModel;
import com.elotech.biblioteca.models.LivroModel;
import com.elotech.biblioteca.models.UsuarioModel;
import com.elotech.biblioteca.services.EmprestimoService;
import com.elotech.biblioteca.services.LivroService;
import com.elotech.biblioteca.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private EmprestimoModel toEntity(EmprestimoRequestDTO request, LivroModel livro, UsuarioModel usuario) {
        if (request == null) return null;

        EmprestimoModel emprestimo = new EmprestimoModel();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(request.getDataEmprestimo() != null
                ? request.getDataEmprestimo()
                : LocalDate.now());
        emprestimo.setDataDevolucao(request.getDataDevolucao());
        emprestimo.setStatus(request.getStatus() != null
                ? request.getStatus()
                : EmprestimoStatus.ATIVO);

        return emprestimo;
    }

    private EmprestimoResponseDTO toResponseDTO(EmprestimoModel emprestimo) {
        if (emprestimo == null) return null;

        EmprestimoResponseDTO dto = new EmprestimoResponseDTO();
        dto.setId(emprestimo.getId());
        dto.setDataEmprestimo(emprestimo.getDataEmprestimo());
        dto.setDataDevolucao(emprestimo.getDataDevolucao());
        dto.setStatus(emprestimo.getStatus());

        // Converte Livro para DTO (reutiliza lógica)
        dto.setLivro(toLivroDTO(emprestimo.getLivro()));

        // Converte Usuario para DTO (reutiliza lógica)
        dto.setUsuario(toUsuarioDTO(emprestimo.getUsuario()));

        return dto;
    }

    private LivroDTO toLivroDTO(LivroModel livro) {
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

    private UsuarioDTO toUsuarioDTO(UsuarioModel usuario) {
        if (usuario == null) return null;

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setDataCadastro(usuario.getDataCadastro());
        return dto;
    }

    private void updateEntity(EmprestimoModel emprestimo, EmprestimoRequestDTO request) {
        if (request.getDataDevolucao() != null) {
            emprestimo.setDataDevolucao(request.getDataDevolucao());
        }
        if (request.getStatus() != null) {
            emprestimo.setStatus(request.getStatus());
        }
    }

    public EmprestimoController(EmprestimoService emprestimoService,
                                LivroService livroService,
                                UsuarioService usuarioService) {
        this.emprestimoService = emprestimoService;
        this.livroService = livroService;
        this.usuarioService = usuarioService;
    }

    private final EmprestimoService emprestimoService;
    private final LivroService livroService;
    private final UsuarioService usuarioService;

    @Operation(summary = "Cria um novo empréstimo")
    @ApiResponse(responseCode = "201", description = "Empréstimo criado com sucesso")
    @PostMapping
    public ResponseEntity<EmprestimoResponseDTO> criarEmprestimo(
            @Valid @RequestBody EmprestimoRequestDTO requestDTO) {  // ← ENTIDADE, não DTO!

        LivroModel livro = livroService.buscarLivroPorId(requestDTO.getLivroId());
        UsuarioModel usuario = usuarioService.buscarUsuarioPorId(requestDTO.getUsuarioId());

        EmprestimoModel emprestimo = toEntity(requestDTO, livro, usuario);

        livroService.atualizarLivro(livro.getId(), livro);

        EmprestimoModel saved = emprestimoService.criarEmprestimo(emprestimo);

        EmprestimoResponseDTO responseDTO = toResponseDTO(saved);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @Operation(summary = "Lista todos os empréstimos")
    @ApiResponse(responseCode = "200", description = "Empréstimos listados com sucesso")
    @GetMapping
    public ResponseEntity<List<EmprestimoResponseDTO>> buscarTodosOsEmprestimos() {
        List<EmprestimoModel> request = emprestimoService.buscarTodosOsEmprestimos();

        List<EmprestimoResponseDTO> dtos = request.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Busca um único empréstimo")
    @ApiResponse(responseCode = "200", description = "Empréstimo encontrado com sucesso")
    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoResponseDTO> buscarEmprestimoPorId(@PathVariable Long id) {
        try {
            EmprestimoModel emprestimo = emprestimoService.buscarEmprestimoPorId(id);
            EmprestimoResponseDTO dto = toResponseDTO(emprestimo);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Atualiza as informações de um empréstimo")
    @ApiResponse(responseCode = "200", description = "Empréstimo atualizado com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<EmprestimoResponseDTO> atualizarEmprestimo(
            @PathVariable Long id,
            @RequestBody EmprestimoRequestDTO requestDTO) {
        try {
            EmprestimoModel emprestimo = emprestimoService.buscarEmprestimoPorId(id);

            updateEntity(emprestimo, requestDTO);

            EmprestimoModel updated = emprestimoService.atualizarEmprestimo(id, emprestimo);

            return ResponseEntity.ok(toResponseDTO(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Deleta um empréstimo da tabela")
    @ApiResponse(responseCode = "204", description = "Empréstimo deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmprestimoPorId(@PathVariable Long id) {
        try {
            EmprestimoModel emprestimo = emprestimoService.buscarEmprestimoPorId(id);

            emprestimoService.excluirEmprestimo(id);

            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}