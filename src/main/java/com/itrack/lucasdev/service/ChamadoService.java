package com.itrack.lucasdev.service;

import com.itrack.lucasdev.dto.ChamadoRequest;
import com.itrack.lucasdev.dto.ChamadoResponse;
import com.itrack.lucasdev.dto.ChamadoUpdRequest;
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

    public ChamadoService(ChamadoRepository chamadoRepository,
                          UsuarioRepository usuarioRepository,
                          EquipamentoRepository equipamentoRepository) {
        this.chamadoRepository = chamadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.equipamentoRepository = equipamentoRepository;
    }

    public ChamadoResponse salvarChamado(ChamadoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Chamado chamado = new Chamado();
        chamado.setTitulo(request.titulo());
        chamado.setDescricao(request.descricao());
        chamado.setUsuario(usuario);
        chamado.setDataAbertura(LocalDateTime.now());
        chamado.setStatus(Chamado.StatusChamado.ABERTO);

        if (request.equipamentoId() != null) {
            Equipamento equipamento = equipamentoRepository.findById(request.equipamentoId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Equipamento não encontrado"));
            chamado.setEquipamento(equipamento);
        }

        return ChamadoResponse.de(chamadoRepository.save(chamado));
    }

    public List<ChamadoResponse> listarChamados() {
        return chamadoRepository.findAll().stream()
                .map(ChamadoResponse::de)
                .toList();
    }

    public ChamadoResponse buscarChamado(Integer id) {
        return ChamadoResponse.de(buscarEntidade(id));
    }

    public ChamadoResponse atualizar(Integer id, ChamadoUpdRequest dados) {
        Chamado chamado = buscarEntidade(id);

        if (dados.titulo() != null) {
            chamado.setTitulo(dados.titulo());
        }

        if (dados.descricao() != null) {
            chamado.setDescricao(dados.descricao());
        }

        if (dados.status() != null) {
            chamado.setStatus(dados.status());
        }

        return ChamadoResponse.de(chamadoRepository.save(chamado));
    }

    public void deletar(Integer id) {
        chamadoRepository.delete(buscarEntidade(id));
    }

    // Uso interno: devolve a entidade (nunca exponha isso no controller).
    private Chamado buscarEntidade(Integer id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Chamado não encontrado"));
    }
}