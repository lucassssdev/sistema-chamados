package lucasdev.sistema_de_chamados.service;

import org.springframework.stereotype.Service;
import lucasdev.sistema_de_chamados.infraestructure.entities.Chamado;
import lucasdev.sistema_de_chamados.infraestructure.entities.Chamado.StatusChamado;
import lucasdev.sistema_de_chamados.infraestructure.repository.ChamadoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChamadoService {
    private final ChamadoRepository chamadoRepository;
    private final UsuarioService usuarioService;

    public ChamadoService(ChamadoRepository chamadoRepository, UsuarioService usuarioService) {
        this.chamadoRepository = chamadoRepository;
        this.usuarioService = usuarioService;
    }

    public List<Chamado> listarChamados() {
        return chamadoRepository.findAll();
    }

    public Chamado salvarChamado(Chamado chamado) {

        usuarioService.buscarPorId(chamado.getUsuarioId());
        
        chamado.setDataAbertura(LocalDateTime.now());

        if (chamado.getStatus() == null) {
            chamado.setStatus(StatusChamado.ABERTO);
        }

        return chamadoRepository.save(chamado);
    }

    public Chamado buscarPorId(String id) {
        return chamadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Registro de chamado não encontrado!"));
    }

    public Chamado atualizar(String id, Chamado chamadoAtualizado) {

        Chamado chamadoExistente = buscarPorId(id);

        chamadoExistente.setTitulo(chamadoAtualizado.getTitulo());
        chamadoExistente.setDescricao(chamadoAtualizado.getDescricao());
        chamadoExistente.setStatus(chamadoAtualizado.getStatus());

        return chamadoRepository.save(chamadoExistente);
    }

    public void deletar(String id) {

        Chamado chamado = buscarPorId(id);

        chamadoRepository.delete(chamado);
    }
}
