package gm.pci.servicio;


import gm.pci.modelo.Categoria;
import gm.pci.repositorio.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServicio implements ICategoriaServicio{
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Override
    public List<Categoria> listarCategorias() {
        return categoriaRepositorio.findAll()
                .stream()
                .sorted(Comparator.comparing(Categoria::getCategoriaID))
                .collect(Collectors.toList());
    }

    @Override
    public Categoria buscarCategoriaPorId(Integer categoriaid) {
        Categoria categoria = categoriaRepositorio.findById(categoriaid).orElse(null);
        return categoria;
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepositorio.save(categoria);
    }

    @Override
    public void eliminarCategoria(Categoria categoria) {
        categoriaRepositorio.delete(categoria);

    }
}
