package ipss.TechSolutions.repository;

import ipss.TechSolutions.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Esta función la usaremos después para verificar si el correo ya existe
    Optional<Usuario> findByCorreo(String correo);
}
