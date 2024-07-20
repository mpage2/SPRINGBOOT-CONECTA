package gm.pci.repositorio;

import gm.pci.modelo.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngresoRepositorio extends JpaRepository<Ingreso, Integer> {
}
