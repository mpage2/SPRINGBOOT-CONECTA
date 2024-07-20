package gm.pci.controlador;

import gm.pci.dto.ProveedorDTO;
import gm.pci.excepcion.RecursoNoEncontradoExcepcion;
import gm.pci.modelo.Proveedor;
import gm.pci.servicio.IProveedorServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pci-app")
@CrossOrigin
public class ProveedorControlador {

    private static final Logger logger = LoggerFactory.getLogger(ProveedorControlador.class);

    @Autowired
    private IProveedorServicio proveedorServicio;

    @GetMapping("/proveedores")
    public List<ProveedorDTO> obtenerProveedores() {
        var proveedores = proveedorServicio.listarProveedores();
        return proveedores.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/proveedores/guardar")
    public ResponseEntity<?> agregarProveedor(@RequestBody ProveedorDTO proveedorDTO) {
        // Validar campos antes de guardar
        String mensajeError = validarProveedor(proveedorDTO);
        if (mensajeError != null) {
            return ResponseEntity.badRequest().body(mensajeError);
        }

        // Verificar duplicados
        if (esDuplicado(proveedorDTO)) {
            return ResponseEntity.badRequest().body("Ya existe un proveedor con los mismos datos.");
        }

        logger.info("Proveedor a agregar: " + proveedorDTO);
        Proveedor proveedor = convertirAEntidad(proveedorDTO);
        Proveedor proveedorGuardado = proveedorServicio.guardarProveedor(proveedor);
        return ResponseEntity.ok(convertirADTO(proveedorGuardado));
    }

    @PutMapping("/proveedores/actualizar/{id}")
    public ResponseEntity<?> actualizarProveedor(@PathVariable Integer id, @RequestBody ProveedorDTO proveedorDTO) {
        // Validar campos antes de actualizar
        String mensajeError = validarProveedor(proveedorDTO);
        if (mensajeError != null) {
            return ResponseEntity.badRequest().body(mensajeError);
        }

        Proveedor proveedor = proveedorServicio.buscarProveedorPorId(id);
        if (proveedor == null)
            throw new RecursoNoEncontradoExcepcion("El ID de proveedor no existe: " + id);

        // Verificar duplicados
        if (esDuplicado(proveedorDTO, id)) {
            return ResponseEntity.badRequest().body("Ya existe un proveedor con los mismos datos.");
        }

        proveedor.setEmpresa(proveedorDTO.getEmpresa());
        proveedor.setUbicacion(proveedorDTO.getUbicacion());
        proveedor.setTelefono(proveedorDTO.getTelefono());
        proveedor.setEmail(proveedorDTO.getEmail());
        proveedor.setRUC(proveedorDTO.getRUC());
        proveedorServicio.guardarProveedor(proveedor);
        return ResponseEntity.ok(convertirADTO(proveedor));
    }

    @DeleteMapping("/proveedores/eliminar/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarProveedor(@PathVariable Integer id) {
        Proveedor proveedor = proveedorServicio.buscarProveedorPorId(id);
        if (proveedor == null)
            throw new RecursoNoEncontradoExcepcion("El ID de proveedor no existe: " + id);

        proveedorServicio.eliminarProveedor(proveedor);
        logger.info("Proveedor eliminado con éxito.");
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

    // Métodos de conversión entre DTO y entidad
    private ProveedorDTO convertirADTO(Proveedor proveedor) {
        ProveedorDTO proveedorDTO = new ProveedorDTO();
        proveedorDTO.setProveedorID(proveedor.getProveedorID());
        proveedorDTO.setEmpresa(proveedor.getEmpresa());
        proveedorDTO.setUbicacion(proveedor.getUbicacion());
        proveedorDTO.setTelefono(proveedor.getTelefono());
        proveedorDTO.setEmail(proveedor.getEmail());
        proveedorDTO.setRUC(proveedor.getRUC());
        return proveedorDTO;
    }

    private Proveedor convertirAEntidad(ProveedorDTO proveedorDTO) {
        Proveedor proveedor = new Proveedor();
        proveedor.setProveedorID(proveedorDTO.getProveedorID());
        proveedor.setEmpresa(proveedorDTO.getEmpresa());
        proveedor.setUbicacion(proveedorDTO.getUbicacion());
        proveedor.setTelefono(proveedorDTO.getTelefono());
        proveedor.setEmail(proveedorDTO.getEmail());
        proveedor.setRUC(proveedorDTO.getRUC());
        return proveedor;
    }

    // Método para validar campos del proveedor y devolver mensajes de error
    private String validarProveedor(ProveedorDTO proveedor) {
        if (!validarEmpresa(proveedor.getEmpresa())) {
            return "El nombre de la empresa solo puede contener letras, la letra 'ñ' y espacios.";
        }

        if (!validarUbicacion(proveedor.getUbicacion())) {
            return "La ubicación solo puede contener letras, la letra 'ñ' y espacios.";
        }

        if (!validarTelefono(proveedor.getTelefono())) {
            return "El teléfono debe empezar con el número 9 y tener 9 dígitos.";
        }

        if (!validarEmail(proveedor.getEmail())) {
            return "El email debe contener un '@'.";
        }

        if (!validarRUC(proveedor.getRUC())) {
            return "El RUC debe empezar con 10 o 20 y tener 11 dígitos.";
        }

        return null; // Retorna null si todas las validaciones pasan
    }

    private boolean validarEmpresa(String empresa) {
        // Solo letras, la letra 'ñ' y espacios
        return empresa.matches("^[a-zA-ZñÑ\\sáéíóúÁÉÍÓÚ]+$");
    }

    private boolean validarUbicacion(String ubicacion) {
        // Solo letras, la letra 'ñ' y espacios
        return ubicacion.matches("^[a-zA-ZñÑ\\sáéíóúÁÉÍÓÚ]+$");
    }

    private boolean validarTelefono(String telefono) {
        // Números, debe empezar con 9 y tener 9 dígitos
        return telefono.matches("^9\\d{8}$");
    }

    private boolean validarEmail(String email) {
        // Debe contener un '@'
        return email.contains("@");
    }

    private boolean validarRUC(String ruc) {
        // Solo números, debe empezar con 10 o 20 y tener 11 dígitos
        return ruc.matches("^(10|20)\\d{9}$");
    }

    private boolean esDuplicado(ProveedorDTO proveedorDTO) {
        return proveedorServicio.listarProveedores().stream()
                .anyMatch(p -> p.getEmail().equalsIgnoreCase(proveedorDTO.getEmail()) ||
                        p.getRUC().equals(proveedorDTO.getRUC()) ||
                        p.getEmpresa().equalsIgnoreCase(proveedorDTO.getEmpresa()));
    }

    private boolean esDuplicado(ProveedorDTO proveedorDTO, Integer id) {
        return proveedorServicio.listarProveedores().stream()
                .anyMatch(p -> !p.getProveedorID().equals(id) &&
                        (p.getEmail().equalsIgnoreCase(proveedorDTO.getEmail()) ||
                                p.getRUC().equals(proveedorDTO.getRUC()) ||
                                p.getEmpresa().equalsIgnoreCase(proveedorDTO.getEmpresa())));
    }
}
