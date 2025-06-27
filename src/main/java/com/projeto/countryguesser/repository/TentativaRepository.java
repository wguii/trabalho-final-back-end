package com.projeto.countryguesser.repository;

import com.projeto.countryguesser.entities.Tentativa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TentativaRepository extends JpaRepository<Tentativa, Long> {
    List<Tentativa> findByUsuarioId(Long usuarioId);
}
