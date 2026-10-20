package com.itrack.lucasdev.controller;

import com.itrack.lucasdev.dto.ChamadoRequest;
import com.itrack.lucasdev.dto.ChamadoResponse;
import com.itrack.lucasdev.dto.ChamadoUpdRequest;
import com.itrack.lucasdev.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService chamadoService;

    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201 em vez de 200
    public ChamadoResponse salvarChamado(@Valid @RequestBody ChamadoRequest request) {
        return chamadoService.salvarChamado(request);
    }

    @GetMapping
    public List<ChamadoResponse> listarChamados() {
        return chamadoService.listarChamados();
    }

    @GetMapping("/{id}")
    public ChamadoResponse buscarChamado(@PathVariable Integer id) {
        return chamadoService.buscarChamado(id);
    }

    @PatchMapping("/{id}")
    public ChamadoResponse atualizar(@PathVariable Integer id,
                                     @Valid @RequestBody ChamadoUpdRequest dados) {
        return chamadoService.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deletar(@PathVariable Integer id) {
        chamadoService.deletar(id);
    }
}