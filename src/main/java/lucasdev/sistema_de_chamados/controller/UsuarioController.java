package lucasdev.sistema_de_chamados.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import lucasdev.sistema_de_chamados.service.UsuarioService;
import lucasdev.sistema_de_chamados.infraestructure.entities.Usuario;

@RestController
@ControllerAdvice
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public class EmailExistente extends RuntimeException {
        public EmailExistente(String email) {
            super("Já existe um usuário com e-mail " + email);
        }
    }

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listarUsuarios();
    }

    @PostMapping("/cadastrar")
    public Usuario criar(@RequestBody Usuario usuario) {
        return usuarioService.salvarUsuario(usuario);
    }

    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario usuario) {
        return usuarioService.loginUsuario(
            usuario.getEmail(),
            usuario.getSenha()
        );
    }
    
}