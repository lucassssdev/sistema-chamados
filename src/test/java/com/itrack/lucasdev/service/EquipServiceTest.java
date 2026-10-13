package com.itrack.lucasdev.service;

import com.itrack.lucasdev.model.Equipamento;
import com.itrack.lucasdev.model.Setor;
import com.itrack.lucasdev.repository.EquipamentoRepository;
import com.itrack.lucasdev.repository.SetorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EquipServiceTest {

    @Mock
    private EquipamentoRepository equipamentoRepository;

    @Mock
    private SetorRepository setorRepository;

    @InjectMocks
    private EquipamentoService equipamentoService;

    @Test
    void deveRejeitarPatrimonioDuplicado() {

        Equipamento equipamento = new Equipamento();
        equipamento.setPatrimonio(1001);

        when(equipamentoRepository.existsByPatrimonio(1001))
                .thenReturn(true);

        assertThrows(
                ResponseStatusException.class,
                () -> equipamentoService.salvarEquip(equipamento)
        );

        verify(equipamentoRepository, never())
                .save(any(Equipamento.class));
    }

    @Test
    void deveCadastrarEquipamentoValido() {

        Setor setor = new Setor();
        setor.setId(1);
        setor.setNome("TI");

        Equipamento equipamento = new Equipamento();
        equipamento.setNome("PC TI");
        equipamento.setPatrimonio(1001);
        equipamento.setSetor(setor);

        when(equipamentoRepository.existsByPatrimonio(1001))
                .thenReturn(false);

        when(setorRepository.findById(1))
                .thenReturn(Optional.of(setor));

        when(equipamentoRepository.save(any(Equipamento.class)))
                .thenAnswer(invocacao -> invocacao.getArgument(0));

        Equipamento resultado =
                equipamentoService.salvarEquip(equipamento);

        assertEquals("PC TI", resultado.getNome());

        verify(equipamentoRepository).save(any(Equipamento.class));
    }

    @Test
    void deveRejeitarSetorInexistente() {

        Setor setor = new Setor();
        setor.setId(999);

        Equipamento equipamento = new Equipamento();
        equipamento.setNome("Computador");
        equipamento.setPatrimonio(1002);
        equipamento.setSetor(setor);

        when(setorRepository.findById(999)).thenReturn(Optional.empty());

        ResponseStatusException erro = assertThrows(ResponseStatusException.class, () -> equipamentoService.salvarEquip(equipamento));

        assertEquals(HttpStatus.NOT_FOUND, erro.getStatusCode());

        verify(equipamentoRepository, never()).save(any(Equipamento.class));
    }

    @Test
    void deveAtualizarSomenteDescricao() {

        Equipamento equipamento = new Equipamento();
        equipamento.setId(1);
        equipamento.setNome("PC TI");
        equipamento.setPatrimonio(1001);
        equipamento.setTipo("Desktop");
        equipamento.setDescricao("Computador antigo");

        Equipamento dados = new Equipamento();
        dados.setDescricao("Computador atualizado");

        when(equipamentoRepository.findById(1))
                .thenReturn(Optional.of(equipamento));

        when(equipamentoRepository.save(any(Equipamento.class)))
                .thenAnswer(invocacao -> invocacao.getArgument(0));

        Equipamento resultado = equipamentoService.atualizar(1, dados);

        assertEquals("Computador atualizado", resultado.getDescricao());
        assertEquals("PC TI", resultado.getNome());
        assertEquals(1001, resultado.getPatrimonio());
        assertEquals("Desktop", resultado.getTipo());

        verify(equipamentoRepository, times(1))
                .save(equipamento);
    }
}
