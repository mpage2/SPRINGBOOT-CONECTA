package gm.pci.servicio;

import gm.pci.modelo.Ingreso;

import java.util.List;

public interface IIngresoServicio {

    public List<Ingreso> listarIngresos();

    public Ingreso guardarIngreso(Ingreso ingreso);


}
