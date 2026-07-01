package com.elotech.biblioteca.repositories;

import com.elotech.biblioteca.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
}
