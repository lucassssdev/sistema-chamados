package com.itrack.lucasdev.service;

import com.itrack.lucasdev.model.Equipamento;
import com.itrack.lucasdev.model.Setor;
import com.itrack.lucasdev.repository.EquipamentoRepository;
import com.itrack.lucasdev.repository.SetorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;
    private final SetorRepository setorRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository, SetorRepository setorRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.setorRepository = setorRepository;
    }

    public Equipamento salvarEquip(Equipamento equipamento) {
        boolean patrimonioEmUso = equipamentoRepository.existsByPatrimonio(equipamento.getPatrimonio());

        if (patrimonioEmUso) {
            throw new RuntimeException(
                    "Este patrimônio já está sendo utilizado."
            );
        }

        Setor setor = setorRepository.findById(equipamento.getSetor().getId()).orElseThrow(() -> new RuntimeException("Setor não encontrado"));

        equipamento.setSetor(setor);

        return equipamentoRepository.save(equipamento);
    }

    public List<Equipamento> listarEquip() {
        return equipamentoRepository.findAll();
    }

    public Equipamento buscarEquip(Integer id) {
        return equipamentoRepository.findById(id).orElse(null);
    }

    public Equipamento atualizar(Integer id, Equipamento dados) {

        Equipamento equipamento = buscarEquip(id);

        if (dados.getNome() != null) {
            equipamento.setNome(dados.getNome());
        }

        if (dados.getDescricao() != null) {
            equipamento.setDescricao(dados.getDescricao());
        }

        if (dados.getTipo() != null) {
            equipamento.setTipo(dados.getTipo());
        }

        if (dados.getPatrimonio() != null) {

            boolean patrimonioEmUso = equipamentoRepository.existsByPatrimonioAndIdNot(dados.getPatrimonio(), id);

            if (patrimonioEmUso) {
                throw new RuntimeException(
                        "Este patrimônio já está sendo utilizado."
                );
            }

            equipamento.setPatrimonio(dados.getPatrimonio());
        }

        if (dados.getSetor() != null && dados.getSetor().getId() != null) {
            Setor setor = setorRepository.findById(dados.getSetor().getId()).orElseThrow(() -> new RuntimeException("Setor não encontrado"));

            equipamento.setSetor(setor);
        }

        return equipamentoRepository.save(equipamento);
    }

    public void deletar(Integer id) {
        Equipamento equipamento = buscarEquip(id);
        equipamentoRepository.delete(equipamento);
    }
}
