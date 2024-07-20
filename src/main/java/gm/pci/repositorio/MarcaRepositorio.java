package gm.pci.repositorio;


import gm.pci.modelo.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepositorio extends JpaRepository<Marca, Integer> {
}
