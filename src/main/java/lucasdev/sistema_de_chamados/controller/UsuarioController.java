package lucasdev.sistema_de_chamados.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lucasdev.sistema_de_chamados.service.UsuarioService;
import lucasdev.sistema_de_chamados.infraestructure.entities.Usuario;

@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public Usuario home() {
        return usuarioService.buscarUsuario();
    }
}