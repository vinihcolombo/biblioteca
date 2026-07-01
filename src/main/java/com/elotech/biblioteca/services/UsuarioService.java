package com.elotech.biblioteca.services;

import com.elotech.biblioteca.models.UsuarioModel;
import com.elotech.biblioteca.repositories.UsuarioRepository;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    private final UsuarioRepository usuarioRepository;

    public List<UsuarioModel> buscarTodosOsUsuarios() {
        return usuarioRepository.findAll();
    }

    public UsuarioModel criarUsuario (UsuarioModel usuarioModel){
        return usuarioRepository.save(usuarioModel);
    }

    public UsuarioModel buscarUsuarioPorId(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public UsuarioModel atualizarUsuario (Long id, UsuarioModel usuarioModel){
        UsuarioModel model = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        model.setNome(usuarioModel.getNome());
        model.setTelefone(usuarioModel.getTelefone());
        return usuarioRepository.save(model);
    }

    public void excluirUsuario (Long id){
        usuarioRepository.deleteById(id);
    }
}