package gm.pci.controlador;

import gm.pci.dto.SalidaDTO;
import gm.pci.modelo.Salida;
import gm.pci.servicio.ISalidaServicio;
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
public class SalidaControlador {

    private static final Logger logger = LoggerFactory.getLogger(SalidaControlador.class);

    @Autowired
    private ISalidaServicio salidaServicio;

    @GetMapping("/salidas")
    public List<SalidaDTO> obtenerSalidas() {
        List<Salida> salidas = salidaServicio.listarSalidas();
        logger.info("Salidas obtenidas: " + salidas.size());

        // Convertir lista de Salida a lista de SalidaDTO
        List<SalidaDTO> salidasDTO = salidas.stream()
                .map(this::convertirASalidaDTO)
                .collect(Collectors.toList());

        return salidasDTO;
    }

    @PostMapping("/salidas/guardar")
    public ResponseEntity<SalidaDTO> agregarSalida(@RequestBody SalidaDTO salidaDTO) {
        logger.info("Salida a agregar: " + salidaDTO);

        // Convertir SalidaDTO a Salida para guardar en la base de datos
        Salida salida = convertirASalida(salidaDTO);
        Salida salidaGuardada = salidaServicio.guardarSalida(salida);

        // Convertir la Salida guardada de vuelta a SalidaDTO para devolverla como respuesta
        SalidaDTO salidaGuardadaDTO = convertirASalidaDTO(salidaGuardada);

        return ResponseEntity.ok(salidaGuardadaDTO);
    }

    // Método para convertir de Salida a SalidaDTO
    private SalidaDTO convertirASalidaDTO(Salida salida) {
        return new SalidaDTO(
                salida.getSalidaID(),
                salida.getProductoID(),
                salida.getMarcaid(),
                salida.getCantidad(),
                salida.getPrecio(),
                salida.getCategoriaid(),
                salida.getProveedorID(),
                salida.getUsuarioID()
        );
    }

    // Método para convertir de SalidaDTO a Salida
    private Salida convertirASalida(SalidaDTO salidaDTO) {
        return new Salida(
                salidaDTO.getSalidaID(),
                salidaDTO.getProductoID(),
                salidaDTO.getMarcaid(),
                salidaDTO.getCantidad(),
                salidaDTO.getPrecio(),
                salidaDTO.getCategoriaid(),
                salidaDTO.getProveedorID(),
                salidaDTO.getUsuarioID()
        );
    }
}
