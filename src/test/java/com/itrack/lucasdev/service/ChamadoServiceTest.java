package com.itrack.lucasdev.service;

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

        Chamado chamado = new Chamado();
        chamado.setTitulo("Computador explodiu! :(");
        chamado.setDescricao("O computador explodiu vei");
        chamado.setUsuario(usuario);

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(usuario));

        when(chamadoRepository.save(any(Chamado.class)))
                .thenAnswer(invocacao -> invocacao.getArgument(0));

        Chamado resultado = chamadoService.salvarChamado(chamado);

        assertEquals(Chamado.StatusChamado.ABERTO, resultado.getStatus());
    }

    @Test
    void deveRejeitarChamadoCasoUsuarioInexistente() {

        Usuario usuario = new Usuario();
        usuario.setId(999);

        Chamado chamado = new Chamado();
        chamado.setTitulo("Computador explodiu! :(");
        chamado.setDescricao("O computador explodiu vei");
        chamado.setUsuario(usuario);

        when(usuarioRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> chamadoService.salvarChamado(chamado)
        );

        verify(chamadoRepository, never())
                .save(any(Chamado.class));
    }

    @Test
    void devePermitirChamadoSemEquipamento() {

        Usuario usuario = new Usuario();
        usuario.setId(1);

        Chamado chamado = new Chamado();
        chamado.setTitulo("Problema de acesso");
        chamado.setDescricao("Usuário não consegue acessar o sistema");
        chamado.setUsuario(usuario);

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(usuario));

        when(chamadoRepository.save(any(Chamado.class)))
                .thenAnswer(invocacao -> invocacao.getArgument(0));

        Chamado resultado = chamadoService.salvarChamado(chamado);

        assertNotNull(resultado);
        assertNull(resultado.getEquipamento());
        assertEquals(Chamado.StatusChamado.ABERTO, resultado.getStatus());
        assertNotNull(resultado.getDataAbertura());

        verify(chamadoRepository, times(1))
                .save(any(Chamado.class));

        verifyNoInteractions(equipamentoRepository);
    }
}
