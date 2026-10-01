package com.itrack.lucasdev.repository;

import com.itrack.lucasdev.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
