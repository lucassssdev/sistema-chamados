package com.itrack.lucasdev.service;

import com.itrack.lucasdev.model.Setor;
import com.itrack.lucasdev.model.Usuario;
import com.itrack.lucasdev.repository.SetorRepository;
import com.itrack.lucasdev.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

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
        Integer idSetor = usuario.getSetor().getId();

        Setor setor = setorRepository.findById(idSetor).orElseThrow(() -> new RuntimeException("Setor não encontrado"));

        usuario.setSetor(setor);

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuario(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario atualizar(Integer id, Usuario dados) {
        Usuario usuario = buscarUsuario(id);
        usuario.setNome(dados.getNome());

        return usuarioRepository.save(usuario);
    }

    public void deletar(Integer id) {
        Usuario usuario = buscarUsuario(id);
        usuarioRepository.delete(usuario);
    }
}
