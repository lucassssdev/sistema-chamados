package com.itrack.lucasdev.repository;

import com.itrack.lucasdev.model.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Integer> {
    boolean existsByPatrimonio(Integer patrimonio);

    boolean existsByPatrimonioAndIdNot(Integer patrimonio, Integer id);
}
