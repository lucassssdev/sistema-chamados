package com.itrack.lucasdev.repository;

import com.itrack.lucasdev.model.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {
}