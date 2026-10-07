package com.itrack.lucasdev.service;

import com.itrack.lucasdev.model.Equipamento;
import com.itrack.lucasdev.model.Setor;
import com.itrack.lucasdev.repository.EquipamentoRepository;
import com.itrack.lucasdev.repository.SetorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        if (equipamentoRepository.existsByPatrimonio(equipamento.getPatrimonio())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este patrimônio já está sendo utilizado"
            );
        }

        if (equipamento.getSetor() == null || equipamento.getSetor().getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O setor do equipamento é obrigatório"
            );
        }

        Setor setor = setorRepository.findById(equipamento.getSetor().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Setor não encontrado"
                ));

        equipamento.setSetor(setor);
        return equipamentoRepository.save(equipamento);
    }

    public List<Equipamento> listarEquip() {
        return equipamentoRepository.findAll();
    }

    public Equipamento buscarEquip(Integer id) {
        return equipamentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Equipamento não encontrado"
                ));
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
            boolean patrimonioEmUso = equipamentoRepository
                    .existsByPatrimonioAndIdNot(dados.getPatrimonio(), id);

            if (patrimonioEmUso) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Este patrimônio já está sendo utilizado"
                );
            }

            equipamento.setPatrimonio(dados.getPatrimonio());
        }

        if (dados.getSetor() != null && dados.getSetor().getId() != null) {
            Setor setor = setorRepository.findById(dados.getSetor().getId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Setor não encontrado"
                    ));

            equipamento.setSetor(setor);
        }

        return equipamentoRepository.save(equipamento);
    }

    public void deletar(Integer id) {
        Equipamento equipamento = buscarEquip(id);
        equipamentoRepository.delete(equipamento);
    }
}
