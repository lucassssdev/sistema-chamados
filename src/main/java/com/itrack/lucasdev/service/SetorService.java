package com.itrack.lucasdev.service;

import com.itrack.lucasdev.model.Setor;
import com.itrack.lucasdev.repository.SetorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetorService {

    private final SetorRepository setorRepository;

    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    public Setor salvarSetor(Setor setor) {
        return setorRepository.save(setor);
    }

    public List<Setor> listarSetor() {
        return setorRepository.findAll();
    }

    public Setor buscarSetor(Integer id) {
        return setorRepository.findById(id).orElse(null);
    }

    public Setor atualizar(Integer id, Setor dados) {
        Setor setor = buscarSetor(id);

        if (setor.getNome() != null) {
            setor.setNome(dados.getNome());
        }

        return setorRepository.save(setor);

    }

    public void deletar(Integer id) {
        Setor setor = buscarSetor(id);
        setorRepository.delete(setor);
    }
}
