package com.elotech.biblioteca.repositories;

import com.elotech.biblioteca.models.EmprestimoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<EmprestimoModel, Long> {
}
