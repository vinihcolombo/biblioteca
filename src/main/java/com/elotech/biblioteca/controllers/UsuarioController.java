package com.elotech.biblioteca.controllers;

import com.elotech.biblioteca.models.UsuarioModel;
import com.elotech.biblioteca.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> buscarTodosOsUsuarios(){
        List<UsuarioModel> request = usuarioService.buscarTodosOsUsuarios();
        return ResponseEntity.ok().body(request);
    }

    @PostMapping
    public ResponseEntity<UsuarioModel> criarUsuario(@Valid @RequestBody UsuarioModel usuarioModel){
        UsuarioModel request = usuarioService.criarUsuario(usuarioModel);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(request.getId())
                .toUri();

        return ResponseEntity.created(uri).body(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuarioPorId(@Valid @PathVariable Long id){
        usuarioService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> buscarUsuarioPorId(@Valid @PathVariable Long id){
        try {
        UsuarioModel usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModel> atualizarUsuario(
                @Valid
                @PathVariable Long id,
                @RequestBody UsuarioModel  usuarioModel)
    {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, usuarioModel));
    }
}