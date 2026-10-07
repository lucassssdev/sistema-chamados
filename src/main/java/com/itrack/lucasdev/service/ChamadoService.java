package com.itrack.lucasdev.service;

import com.itrack.lucasdev.model.Chamado;
import com.itrack.lucasdev.model.Equipamento;
import com.itrack.lucasdev.model.Usuario;
import com.itrack.lucasdev.repository.ChamadoRepository;
import com.itrack.lucasdev.repository.EquipamentoRepository;
import com.itrack.lucasdev.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EquipamentoRepository equipamentoRepository;

    public ChamadoService(ChamadoRepository chamadoRepository, UsuarioRepository usuarioRepository, EquipamentoRepository equipamentoRepository) {
        this.chamadoRepository = chamadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.equipamentoRepository = equipamentoRepository;
    }

    public Chamado salvarChamado(Chamado chamado) {
        chamado.setDataAbertura(LocalDateTime.now());
        chamado.setStatus(Chamado.StatusChamado.ABERTO);

        if (chamado.getUsuario() == null || chamado.getUsuario().getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O usuário do chamado é obrigatório"
            );
        }

        Usuario usuario = usuarioRepository.findById(chamado.getUsuario().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        chamado.setUsuario(usuario);

        if (chamado.getEquipamento() != null && chamado.getEquipamento().getId() != null) {
            Equipamento equipamento = equipamentoRepository.findById(chamado.getEquipamento().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipamento não encontrado"));

            chamado.setEquipamento(equipamento);
        }

        return chamadoRepository.save(chamado);
    }

    public List<Chamado> listarChamados() {
        return chamadoRepository.findAll();
    }

    public Chamado buscarChamado(Integer id) {
        return chamadoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chamado não encontrado"));
    }

    public Chamado atualizar(Integer id, Chamado dados) {
        Chamado chamado = buscarChamado(id);

        if (dados.getTitulo() != null) {
            chamado.setTitulo(dados.getTitulo());
        }

        if (dados.getDescricao() != null) {
            chamado.setDescricao(dados.getDescricao());
        }

        if (dados.getStatus() != null) {
            chamado.setStatus(dados.getStatus());
        }

        return chamadoRepository.save(chamado);
    }

    public void deletar(Integer id) {
        Chamado chamado = buscarChamado(id);
        chamadoRepository.delete(chamado);
    }
}
