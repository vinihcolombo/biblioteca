package com.elotech.biblioteca.repositories;

import com.elotech.biblioteca.models.LivroModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<LivroModel, Long> {
}