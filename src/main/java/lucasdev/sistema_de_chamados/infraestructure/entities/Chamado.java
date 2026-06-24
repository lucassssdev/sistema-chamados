package lucasdev.sistema_de_chamados.infraestructure.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "chamados")
public class Chamado {
    
    @Id
    private String id;
    private String usuarioId;
    private String titulo;
    private String descricao;
    private StatusChamado status;
    private LocalDateTime dataAbertura;

    public enum StatusChamado {
        ABERTO,
        EM_ANDAMENTO,
        FECHADO,
        CANCELADO
    }

    public Chamado(String id, String usuarioId, String titulo, String descricao, StatusChamado status, LocalDateTime dataAbertura) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.dataAbertura = dataAbertura;
    }

    public Chamado() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioID(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }
}
