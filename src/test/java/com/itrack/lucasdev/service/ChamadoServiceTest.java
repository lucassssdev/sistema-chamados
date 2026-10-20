package com.itrack.lucasdev.service;

import com.itrack.lucasdev.dto.ChamadoRequest;
import com.itrack.lucasdev.dto.ChamadoResponse;
import com.itrack.lucasdev.model.Chamado;
import com.itrack.lucasdev.model.Usuario;
import com.itrack.lucasdev.repository.ChamadoRepository;
import com.itrack.lucasdev.repository.EquipamentoRepository;
import com.itrack.lucasdev.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChamadoServiceTest {

    @Mock
    private ChamadoRepository chamadoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EquipamentoRepository equipamentoRepository;

    @InjectMocks
    private ChamadoService chamadoService;

    @Test
    void deveAbrirChamadoComoAberto() {

        Usuario usuario = new Usuario();
        usuario.setId(1);

        ChamadoRequest request = new ChamadoRequest(
                "Computador explodiu! :(",
                "O computador explodiu vei",
                1,      // usuarioId
                null    // equipamentoId
        );

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(usuario));
        
        when(chamadoRepository.save(any(Chamado.class)))
                .thenAnswer(invocacao -> invocacao.getArgument(0));

        ChamadoResponse resultado = chamadoService.salvarChamado(request);

        assertEquals(Chamado.StatusChamado.ABERTO, resultado.status());
    }

    @Test
    void deveRejeitarChamadoCasoUsuarioInexistente() {

        ChamadoRequest request = new ChamadoRequest(
                "Computador explodiu! :(",
                "O computador explodiu vei",
                999,    // usuarioId que não existe
                null
        );

        when(usuarioRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> chamadoService.salvarChamado(request)
        );

        verify(chamadoRepository, never())
                .save(any(Chamado.class));
    }

    @Test
    void devePermitirChamadoSemEquipamento() {

        Usuario usuario = new Usuario();
        usuario.setId(1);

        ChamadoRequest request = new ChamadoRequest(
                "Problema de acesso",
                "Usuário não consegue acessar o sistema",
                1,
                null
        );

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(usuario));

        when(chamadoRepository.save(any(Chamado.class)))
                .thenAnswer(invocacao -> invocacao.getArgument(0));

        ChamadoResponse resultado = chamadoService.salvarChamado(request);

        assertNotNull(resultado);
        assertNull(resultado.equipamentoId());
        assertEquals(Chamado.StatusChamado.ABERTO, resultado.status());
        assertNotNull(resultado.dataAbertura());

        verify(chamadoRepository, times(1))
                .save(any(Chamado.class));

        verifyNoInteractions(equipamentoRepository);
    }
}