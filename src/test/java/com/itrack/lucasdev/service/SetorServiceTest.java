package com.itrack.lucasdev.service;

import com.itrack.lucasdev.model.Setor;
import com.itrack.lucasdev.repository.SetorRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SetorServiceTest {

    @Mock
    private SetorRepository setorRepository;

    @InjectMocks
    private SetorService setorService;

    @Test
    void deveBuscarSetorExistente() {

        // Criamos um setor fictício
        Setor setor = new Setor();
        setor.setId(1);
        setor.setNome("TI");

        // Simulamos o retorno do Repository
        when(setorRepository.findById(1))
                .thenReturn(Optional.of(setor));

        // Executamos o método real da Service
        Setor resultado = setorService.buscarSetor(1);

        // Verificamos o resultado
        assertEquals("TI", resultado.getNome());
        assertEquals(1, resultado.getId());
    }

    @Test
    void deveRejeitarSetorInexistente() {

        when(setorRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> setorService.buscarSetor(999)
        );
    }
}