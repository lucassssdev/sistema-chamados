package com.itrack.lucasdev.controller;

import com.itrack.lucasdev.model.Chamado;
import com.itrack.lucasdev.service.ChamadoService;
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
    public Chamado salvarChamado(@RequestBody Chamado chamado) {
        return chamadoService.salvarChamado(chamado);
    }

    @GetMapping
    public List<Chamado> listarChamados() {
        return chamadoService.listarChamados();
    }

    @GetMapping("/{id}")
    public Chamado buscarChamado(Integer id) {
        return chamadoService.buscarChamado(id);
    }

    @PatchMapping("/{id}")
    public Chamado atualizar(
            @PathVariable Integer id,
            @RequestBody Chamado dados) {

        return chamadoService.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        chamadoService.deletar(id);
    }
}
