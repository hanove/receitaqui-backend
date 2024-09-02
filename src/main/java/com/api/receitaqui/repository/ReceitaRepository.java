package com.api.receitaqui.repository;

import com.api.receitaqui.model.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    Page<Receita> findByCategoria(String categoria, Pageable pageable);

    Page<Receita> findByNomeContaining(String nome, Pageable pageable);
}
