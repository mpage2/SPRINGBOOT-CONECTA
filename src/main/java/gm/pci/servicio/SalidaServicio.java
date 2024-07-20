package gm.pci.servicio;

import gm.pci.modelo.Salida;
import gm.pci.repositorio.SalidaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalidaServicio implements ISalidaServicio{

    @Autowired
    private SalidaRepositorio salidaRepositorio;
    @Override
    public List<Salida> listarSalidas() {
        return salidaRepositorio.findAll();
    }

    @Override
    public Salida guardarSalida(Salida salida) {
        return salidaRepositorio.save(salida);
    }
}
