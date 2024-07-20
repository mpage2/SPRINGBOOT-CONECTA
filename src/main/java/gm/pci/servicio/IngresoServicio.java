package gm.pci.servicio;

import gm.pci.modelo.Ingreso;
import gm.pci.repositorio.IngresoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngresoServicio implements IIngresoServicio{

    @Autowired
    private IngresoRepositorio ingresoRepositorio;
    @Override
    public List<Ingreso> listarIngresos() {
        return ingresoRepositorio.findAll();
    }



    @Override
    public Ingreso guardarIngreso(Ingreso ingreso) {
        return ingresoRepositorio.save(ingreso);
    }

}
