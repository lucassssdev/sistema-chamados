package com.itrack.lucasdev.dto;

import com.itrack.lucasdev.model.Chamado;
import com.itrack.lucasdev.model.Chamado.StatusChamado;

import java.time.LocalDateTime;

public record ChamadoResponse(
        Integer id,
        String titulo,
        String descricao,
        LocalDateTime dataAbertura,
        StatusChamado status,
        Integer usuarioId,
        String usuarioNome,
        Integer equipamentoId,
        String equipamentoNome
) {

    public static ChamadoResponse de(Chamado chamado) {
        var equipamento = chamado.getEquipamento();

        return new ChamadoResponse(
                chamado.getId(),
                chamado.getTitulo(),
                chamado.getDescricao(),
                chamado.getDataAbertura(),
                chamado.getStatus(),
                chamado.getUsuario().getId(),
                chamado.getUsuario().getNome(),
                equipamento != null ? equipamento.getId() : null,
                equipamento != null ? equipamento.getNome() : null
        );
    }
}