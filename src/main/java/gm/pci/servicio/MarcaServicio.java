package gm.pci.servicio;

import gm.pci.modelo.Marca;
import gm.pci.repositorio.MarcaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarcaServicio implements IMarcaServicio{

    @Autowired
    private MarcaRepositorio marcaRepositorio;
    @Override
    public List<Marca> listarMarcas() {
        return marcaRepositorio.findAll()
                .stream()
                .sorted(Comparator.comparing(Marca::getMarcaID))
                .collect(Collectors.toList());
    }

    @Override
    public Marca buscarMarcaPorId(Integer marcaid) {
        Marca marca = marcaRepositorio.findById(marcaid).orElse(null);
        return marca;

    }

    @Override
    public Marca guardarMarca(Marca marca) {
        return marcaRepositorio.save(marca);
    }

    @Override
    public void eliminarMarca(Marca marca) {
        marcaRepositorio.delete(marca);

    }
}
