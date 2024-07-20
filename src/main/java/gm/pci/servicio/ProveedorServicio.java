package gm.pci.servicio;

import gm.pci.modelo.Proveedor;
import gm.pci.repositorio.ProveedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServicio implements IProveedorServicio {

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;


    @Override
    public List<Proveedor> listarProveedores() {
        return proveedorRepositorio.findAll();
    }

    @Override
    public Proveedor buscarProveedorPorId(Integer ProveedorID) {
        Proveedor proveedor = proveedorRepositorio.findById(ProveedorID).orElse(null);
        return proveedor;
    }

    @Override
    public Proveedor guardarProveedor(Proveedor proveedor) {
        return proveedorRepositorio.save(proveedor);
    }

    @Override
    public void eliminarProveedor(Proveedor proveedor) {
        proveedorRepositorio.delete(proveedor);

    }
}
