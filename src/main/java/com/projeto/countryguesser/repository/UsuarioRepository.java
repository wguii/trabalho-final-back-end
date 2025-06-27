package com.projeto.countryguesser.repository;

import com.projeto.countryguesser.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
