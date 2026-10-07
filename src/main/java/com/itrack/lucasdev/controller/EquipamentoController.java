package com.itrack.lucasdev.controller;

import com.itrack.lucasdev.model.Equipamento;
import com.itrack.lucasdev.service.EquipamentoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    private final EquipamentoService equipamentoService;

    public EquipamentoController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    @PostMapping
    public Equipamento salvar(@Valid @RequestBody Equipamento equipamento) {
        return equipamentoService.salvarEquip(equipamento);
    }

    @GetMapping
    public List<Equipamento> listar() {
        return equipamentoService.listarEquip();
    }

    @GetMapping("/{id}")
    public Equipamento buscarId(@PathVariable Integer id) {
        return equipamentoService.buscarEquip(id);
    }

    @PatchMapping("/{id}")
    public Equipamento atualizar(@PathVariable Integer id, @RequestBody Equipamento equipamento) {
        return equipamentoService.atualizar(id, equipamento);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        equipamentoService.deletar(id);
    }
}
