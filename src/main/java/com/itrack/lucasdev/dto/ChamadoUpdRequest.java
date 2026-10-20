package com.itrack.lucasdev.dto;

import com.itrack.lucasdev.model.Chamado.StatusChamado;
import jakarta.validation.constraints.Size;

public record ChamadoUpdRequest(

        @Size(max = 150, message = "O título deve ter no máximo 150 caracteres")
        String titulo,

        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
        String descricao,

        StatusChamado status
) {
}