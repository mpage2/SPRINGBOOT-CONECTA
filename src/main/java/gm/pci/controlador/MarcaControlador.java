package gm.pci.controlador;

import gm.pci.dto.MarcaDTO;
import gm.pci.excepcion.RecursoNoEncontradoExcepcion;
import gm.pci.modelo.Marca;
import gm.pci.servicio.IMarcaServicio;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pci-app")
@CrossOrigin
public class MarcaControlador {

    private static final Logger logger = LoggerFactory.getLogger(MarcaControlador.class);

    @Autowired
    private IMarcaServicio marcaServicio;

    private static final String NOMBRE_REGEX = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+$";

    @GetMapping("/marcas")
    public List<Marca> obtenerMarcas() {
        List<Marca> marcas = marcaServicio.listarMarcas();
        logger.info("Marcas obtenidas: " + marcas.size());
        return marcas.stream()
                .sorted(Comparator.comparing(Marca::getMarcaID))
                .collect(Collectors.toList());
    }

    @PostMapping("/marcas/guardar")
    public ResponseEntity<?> agregarMarca(@Valid @RequestBody MarcaDTO marcaDTO) {
        if (!esNombreValido(marcaDTO.getMarca())) {
            String mensajeError = "El nombre de la marca solo puede contener letras, espacios y la letra ñ con o sin tilde.";
            return ResponseEntity.badRequest().body(mensajeError);
        }
        Marca marca = convertirADominio(marcaDTO);
        try {
            // Verificar si ya existe una marca con el mismo nombre
            Marca marcaExistente = marcaServicio.listarMarcas().stream()
                    .filter(m -> m.getMarca().equalsIgnoreCase(marcaDTO.getMarca()))
                    .findFirst()
                    .orElse(null);

            if (marcaExistente != null) {
                // Si existe, retornar un mensaje de error
                String mensajeError = "Ya existe una marca con el nombre: " + marcaDTO.getMarca();
                return ResponseEntity.badRequest().body(mensajeError);
            }

            // Guardar la marca
            Marca marcaGuardada = marcaServicio.guardarMarca(marca);
            logger.info("Marca guardada: " + marcaGuardada);
            return ResponseEntity.ok(marcaGuardada);
        } catch (DataIntegrityViolationException e) {
            String mensajeError = "No se puede guardar la marca. El nombre ya está en uso.";
            return ResponseEntity.badRequest().body(mensajeError);
        }
    }

    @PutMapping("/marcas/actualizar/{id}")
    public ResponseEntity<?> actualizarMarca(@PathVariable Integer id, @Valid @RequestBody MarcaDTO marcaDTO) {
        if (!esNombreValido(marcaDTO.getMarca())) {
            String mensajeError = "El nombre de la marca solo puede contener letras, espacios y la letra ñ con o sin tilde.";
            return ResponseEntity.badRequest().body(mensajeError);
        }
        Marca marcaExistente = marcaServicio.buscarMarcaPorId(id);
        if (marcaExistente == null) {
            throw new RecursoNoEncontradoExcepcion("El ID de marca no existe: " + id);
        }

        // Verificar si se está intentando actualizar con un nombre de marca ya existente
        Marca otraMarcaExistente = marcaServicio.listarMarcas().stream()
                .filter(m -> m.getMarca().equalsIgnoreCase(marcaDTO.getMarca()))
                .findFirst()
                .orElse(null);

        if (otraMarcaExistente != null && !otraMarcaExistente.getMarcaID().equals(id)) {
            // Si existe otra marca con el mismo nombre y diferente ID, lanzar excepción
            throw new DataIntegrityViolationException("Ya existe una marca con el nombre: " + marcaDTO.getMarca());
        }

        marcaExistente.setMarca(marcaDTO.getMarca());

        try {
            Marca marcaActualizada = marcaServicio.guardarMarca(marcaExistente);
            logger.info("Marca actualizada: " + marcaActualizada);
            return ResponseEntity.ok(marcaActualizada);
        } catch (DataIntegrityViolationException e) {
            String mensajeError = "No se puede actualizar la marca. El nombre ya está en uso por otra marca.";
            return ResponseEntity.badRequest().body(mensajeError);
        }
    }

    @DeleteMapping("/marcas/eliminar/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarMarca(@PathVariable Integer id) {
        Marca marca = marcaServicio.buscarMarcaPorId(id);
        if (marca == null) {
            throw new RecursoNoEncontradoExcepcion("El ID de marca no existe: " + id);
        }
        marcaServicio.eliminarMarca(marca);
        logger.info("Marca eliminada con éxito.");
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

    private Marca convertirADominio(MarcaDTO marcaDTO) {
        Marca marca = new Marca();
        marca.setMarcaID(marcaDTO.getMarcaID());
        marca.setMarca(marcaDTO.getMarca());
        return marca;
    }

    // Método de validación para el campo marca
    private boolean esNombreValido(String nombre) {
        return Pattern.matches(NOMBRE_REGEX, nombre);
    }
}
