package gm.pci.servicio;

import gm.pci.modelo.Rol;
import gm.pci.repositorio.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServicio implements IRolServicio{
    @Autowired
    private RolRepositorio rolRepositorio;
    @Override
    public List<Rol> listarRoles() {
        return rolRepositorio.findAll();
    }
}
