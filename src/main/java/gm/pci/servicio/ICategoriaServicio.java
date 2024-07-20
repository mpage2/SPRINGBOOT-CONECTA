package gm.pci.servicio;

import gm.pci.modelo.Categoria;

import java.util.List;

public interface ICategoriaServicio {
    public List<Categoria> listarCategorias();

    public Categoria buscarCategoriaPorId(Integer CategoriaID);

    public Categoria guardarCategoria(Categoria categoria);

    public void eliminarCategoria(Categoria categoria);


}

