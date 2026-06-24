package lucasdev.sistema_de_chamados.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lucasdev.sistema_de_chamados.infraestructure.entities.Chamado;
import lucasdev.sistema_de_chamados.service.ChamadoService;


@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService chamadoService;

    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @GetMapping
    public List<Chamado> listar() {
        return chamadoService.listarChamados();
    }

    @PostMapping
    public Chamado criar(@RequestBody Chamado chamado) {
        return chamadoService.salvarChamado(chamado);
    }

    @GetMapping("/{id}")
    public Chamado buscarPorId(@PathVariable String id) {
        return chamadoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
        public Chamado atualizar(
        @PathVariable String id,
        @RequestBody Chamado chamado) {

        return chamadoService.atualizar(id, chamado);
    }

    @DeleteMapping("/{id}")
        public void deletar(@PathVariable String id) {
        chamadoService.deletar(id);
    }
}