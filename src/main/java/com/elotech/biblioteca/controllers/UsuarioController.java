package com.elotech.biblioteca.controllers;

import com.elotech.biblioteca.dtos.UsuarioDTO;
import com.elotech.biblioteca.models.UsuarioModel;
import com.elotech.biblioteca.services.UsuarioService;
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
@RequestMapping("/usuarios")
public class UsuarioController {


    private UsuarioDTO toDTO(UsuarioModel usuario){
        if (usuario == null) return null;

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setEmail(usuario.getEmail());
        dto.setNome(usuario.getNome());
        dto.setTelefone(usuario.getTelefone());
        dto.setDataCadastro(usuario.getDataCadastro());
        return dto;
    }

    private UsuarioModel toEntity(UsuarioDTO dto){
        if (dto == null) return null;

        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(dto.getId());
        usuario.setEmail(dto.getEmail());
        usuario.setNome(dto.getNome());
        usuario.setTelefone(dto.getTelefone());
        usuario.setDataCadastro(dto.getDataCadastro());
        return usuario;
    }

    private void updateEntity(UsuarioModel usuario, UsuarioDTO dto){
        if (dto.getEmail() != null) {
            usuario.setEmail(dto.getEmail());
        }
        if(dto.getNome() != null) {
            usuario.setNome(dto.getNome());
        }
        if(dto.getTelefone() != null) {
            usuario.setTelefone(dto.getTelefone());
        }
        if(dto.getDataCadastro() != null) {
            usuario.setDataCadastro(dto.getDataCadastro());
        }
    }

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    private final UsuarioService usuarioService;

    @Operation(summary = "Lista todos os usuários")
    @ApiResponse(responseCode = "200", description = "Usuários listados com sucesso")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> buscarTodosOsUsuarios(){
        List<UsuarioModel> request = usuarioService.buscarTodosOsUsuarios();

        List<UsuarioDTO> dtos = request.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Cria um novo usuário")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO){

        UsuarioModel usuario = toEntity(usuarioDTO);

        UsuarioModel request = usuarioService.criarUsuario(usuario);

        UsuarioDTO responseDTO = toDTO(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(request.getId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @Operation(summary = "Deleta um usuário da tabela")
    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuarioPorId(@Valid @PathVariable Long id){
        usuarioService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca um único usuário")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@Valid @PathVariable Long id){
        try {
        UsuarioModel usuario = usuarioService.buscarUsuarioPorId(id);
        UsuarioDTO dto = toDTO(usuario);
        return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Atualiza as informações de um usuário")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(
                @Valid
                @PathVariable Long id,
                @RequestBody UsuarioDTO  usuarioDTO)
    {
        UsuarioModel usuario = usuarioService.buscarUsuarioPorId(id);

        updateEntity(usuario, usuarioDTO);

        UsuarioModel updated = usuarioService.atualizarUsuario(id, usuario);

        return ResponseEntity.ok(toDTO(updated));
    }
}