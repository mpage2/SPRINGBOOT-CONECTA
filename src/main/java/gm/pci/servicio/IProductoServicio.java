package gm.pci.servicio;

import gm.pci.modelo.Producto;

import java.util.List;

public interface IProductoServicio {
    public List<Producto> listarProductos();

    public Producto buscarProductoPorId(Integer ProductoID);

    public Producto guardarProducto(Producto producto);

    public void eliminarProducto(Producto producto);
}

