package gm.pci.servicio;

import gm.pci.modelo.Proveedor;

import java.util.List;

public interface IProveedorServicio {

    public List<Proveedor> listarProveedores();

    public Proveedor buscarProveedorPorId(Integer ProveedorID);

    public  Proveedor guardarProveedor(Proveedor proveedor);

    public void eliminarProveedor(Proveedor proveedor);
}
