package lucasdev.sistema_de_chamados.infraestructure.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import lucasdev.sistema_de_chamados.infraestructure.entities.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}