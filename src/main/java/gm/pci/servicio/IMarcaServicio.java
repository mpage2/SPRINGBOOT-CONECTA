package gm.pci.servicio;

import gm.pci.modelo.Marca;

import java.util.List;

public interface IMarcaServicio {
    public List<Marca> listarMarcas();

    public Marca buscarMarcaPorId(Integer MarcaID);

    public Marca guardarMarca(Marca marca);

    public void eliminarMarca(Marca marca);


}