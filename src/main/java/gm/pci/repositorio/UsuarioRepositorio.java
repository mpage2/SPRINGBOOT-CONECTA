package gm.pci.repositorio;

import gm.pci.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    Usuario findByUsuario(String usuario);
}
