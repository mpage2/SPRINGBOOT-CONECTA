package gm.pci.controlador;

import gm.pci.dto.IngresoDTO;
import gm.pci.modelo.Ingreso;
import gm.pci.servicio.IIngresoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pci-app")
@CrossOrigin
public class IngresoControlador {

    private static final Logger logger = LoggerFactory.getLogger(IngresoControlador.class);

    @Autowired
    private IIngresoServicio ingresoServicio;

    @GetMapping("/ingresos")
    public List<IngresoDTO> obtenerIngresos() {
        List<Ingreso> ingresos = ingresoServicio.listarIngresos();
        logger.info("Ingresos obtenidos: " + ingresos.size());

        // Convertir lista de Ingreso a lista de IngresoDTO
        List<IngresoDTO> ingresosDTO = ingresos.stream()
                .map(this::convertirAIngresoDTO)
                .collect(Collectors.toList());

        return ingresosDTO;
    }

    @PostMapping("/ingresos/guardar")
    public ResponseEntity<IngresoDTO> agregarIngreso(@RequestBody IngresoDTO ingresoDTO) {
        logger.info("Ingreso a agregar: " + ingresoDTO);

        // Convertir IngresoDTO a Ingreso para guardar en la base de datos
        Ingreso ingreso = convertirAIngreso(ingresoDTO);
        Ingreso ingresoGuardado = ingresoServicio.guardarIngreso(ingreso);

        // Convertir el Ingreso guardado de vuelta a IngresoDTO para devolverlo como respuesta
        IngresoDTO ingresoGuardadoDTO = convertirAIngresoDTO(ingresoGuardado);

        return ResponseEntity.ok(ingresoGuardadoDTO);
    }

    // Método para convertir de Ingreso a IngresoDTO
    private IngresoDTO convertirAIngresoDTO(Ingreso ingreso) {
        return new IngresoDTO(
                ingreso.getIngresoID(),
                ingreso.getProductoID(),
                ingreso.getMarcaid(),
                ingreso.getCantidad(),
                ingreso.getPrecio(),
                ingreso.getCategoriaid(),
                ingreso.getProveedorID(),
                ingreso.getUsuarioID()
        );
    }

    // Método para convertir de IngresoDTO a Ingreso
    private Ingreso convertirAIngreso(IngresoDTO ingresoDTO) {
        return new Ingreso(
                ingresoDTO.getIngresoID(),
                ingresoDTO.getProductoID(),
                ingresoDTO.getMarcaid(),
                ingresoDTO.getCantidad(),
                ingresoDTO.getPrecio(),
                ingresoDTO.getCategoriaid(),
                ingresoDTO.getProveedorID(),
                ingresoDTO.getUsuarioID()
        );
    }
}
