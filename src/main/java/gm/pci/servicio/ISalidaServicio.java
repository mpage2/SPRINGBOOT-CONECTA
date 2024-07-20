package gm.pci.servicio;

import gm.pci.modelo.Salida;

import java.util.List;

public interface ISalidaServicio {
    public List<Salida> listarSalidas();

    public Salida guardarSalida(Salida salida);

}