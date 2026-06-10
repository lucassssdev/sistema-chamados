package lucasdev.sistema_de_chamados.service;

import org.springframework.stereotype.Service;
import lucasdev.sistema_de_chamados.infraestructure.entities.Usuario;

@Service
public class UsuarioService {

    public Usuario buscarUsuario() {

        Usuario usuario = new Usuario();

        usuario.setNome("Lucas");
        usuario.setEmail("lucas@email.com");

        return usuario;
    }
}