package lucasdev.sistema_de_chamados.infraestructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import lucasdev.sistema_de_chamados.infraestructure.entities.Chamado;

public interface ChamadoRepository extends MongoRepository<Chamado, String> {
}