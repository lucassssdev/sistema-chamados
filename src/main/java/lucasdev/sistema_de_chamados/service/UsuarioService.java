package lucasdev.sistema_de_chamados.service;

import org.springframework.stereotype.Service;
import lucasdev.sistema_de_chamados.infraestructure.entities.Usuario;
import lucasdev.sistema_de_chamados.infraestructure.repository.UsuarioRepository;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario salvarUsuario(Usuario usuario) {

        if (usuario.getNome() == null || usuario.getNome().isBlank()) { 
            throw new RuntimeException("Nome é obrigatório!"); 
        }

        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) { 
            throw new RuntimeException("E-mail é obrigatório!"); 
        }

        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new RuntimeException("Senha é obrigatório!");
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario loginUsuario(String email, String senha) {

        Usuario usuario = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if(!usuario.getSenha().equals(senha)) {
            throw new RuntimeException("Senha inválida");
        }

        return usuario;
    }

    public Usuario buscarPorId(String id) {
        return usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public void deletar(String id) {

        Usuario usuario = buscarPorId(id);

        usuarioRepository.delete(usuario);
    }
}