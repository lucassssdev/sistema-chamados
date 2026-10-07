package com.itrack.lucasdev.service;

import com.itrack.lucasdev.model.Setor;
import com.itrack.lucasdev.model.Usuario;
import com.itrack.lucasdev.repository.SetorRepository;
import com.itrack.lucasdev.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SetorRepository setorRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, SetorRepository setorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.setorRepository = setorRepository;
    }

    public Usuario salvarUsuario(Usuario usuario) {
        if (usuario.getSetor() == null || usuario.getSetor().getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O setor do usuário é obrigatório"
            );
        }

        Setor setor = setorRepository.findById(usuario.getSetor().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Setor não encontrado"
                ));

        usuario.setSetor(setor);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuario(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));
    }

    public Usuario atualizar(Integer id, Usuario dados) {
        Usuario usuario = buscarUsuario(id);

        if (dados.getNome() != null) {
            usuario.setNome(dados.getNome());
        }

        if (dados.getSetor() != null && dados.getSetor().getId() != null) {
            Setor setor = setorRepository.findById(dados.getSetor().getId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Setor não encontrado"
                    ));

            usuario.setSetor(setor);
        }

        return usuarioRepository.save(usuario);
    }

    public void deletar(Integer id) {
        Usuario usuario = buscarUsuario(id);
        usuarioRepository.delete(usuario);
    }
}
