package gm.pci.controlador;

import gm.pci.dto.CategoriaDTO;
import gm.pci.excepcion.RecursoNoEncontradoExcepcion;
import gm.pci.modelo.Categoria;
import gm.pci.servicio.ICategoriaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pci-app")
@CrossOrigin
public class CategoriaControlador {
    private static final Logger logger = LoggerFactory.getLogger(CategoriaControlador.class);

    @Autowired
    private ICategoriaServicio categoriaServicio;

    private static final String NOMBRE_REGEX = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+$";

    @GetMapping("/categorias")
    public List<CategoriaDTO> obtenerCategorias() {
        List<Categoria> categorias = categoriaServicio.listarCategorias();
        logger.info("Categorías obtenidas: " + categorias.size());
        return categorias.stream()
                .sorted(Comparator.comparing(Categoria::getCategoriaID))
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/categorias/guardar")
    public ResponseEntity<?> agregarCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        if (!esNombreValido(categoriaDTO.getNombre())) {
            String mensajeError = "El nombre solo puede contener letras, espacios y la letra ñ con o sin tilde.";
            return ResponseEntity.badRequest().body(mensajeError);
        }
        try {
            // Verificar si ya existe una categoría con el mismo nombre
            Categoria categoriaExistente = categoriaServicio.listarCategorias().stream()
                    .filter(c -> c.getNombre().equalsIgnoreCase(categoriaDTO.getNombre()))
                    .findFirst()
                    .orElse(null);

            if (categoriaExistente != null) {
                // Si existe, retornar un mensaje de error
                String mensajeError = "Ya existe una categoría con el nombre: " + categoriaDTO.getNombre();
                return ResponseEntity.badRequest().body(mensajeError);
            }

            // Guardar la categoría
            Categoria categoria = convertirAEntidad(categoriaDTO);
            Categoria categoriaGuardada = categoriaServicio.guardarCategoria(categoria);
            logger.info("Categoría guardada: " + categoriaGuardada);
            return ResponseEntity.ok(convertirADTO(categoriaGuardada));
        } catch (DataIntegrityViolationException e) {
            // Capturar excepción de violación de restricción única (categoría duplicada)
            String mensajeError = "No se puede guardar la categoría. El nombre ya está en uso.";
            return ResponseEntity.badRequest().body(mensajeError);
        }
    }

    @PutMapping("/categorias/actualizar/{id}")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Integer id, @Valid @RequestBody CategoriaDTO categoriaDTO) {
        if (!esNombreValido(categoriaDTO.getNombre())) {
            String mensajeError = "El nombre solo puede contener letras, espacios y la letra ñ con o sin tilde.";
            return ResponseEntity.badRequest().body(mensajeError);
        }
        try {
            // Buscar la categoría existente por ID
            Categoria categoriaExistente = categoriaServicio.buscarCategoriaPorId(id);
            if (categoriaExistente == null) {
                throw new RecursoNoEncontradoExcepcion("El ID de categoría no existe: " + id);
            }

            // Verificar si se está intentando actualizar con un nombre de categoría ya existente
            Categoria otraCategoriaExistente = categoriaServicio.listarCategorias().stream()
                    .filter(c -> c.getNombre().equalsIgnoreCase(categoriaDTO.getNombre()))
                    .findFirst()
                    .orElse(null);

            if (otraCategoriaExistente != null && !otraCategoriaExistente.getCategoriaID().equals(id)) {
                // Si existe otra categoría con el mismo nombre y diferente ID, lanzar excepción
                throw new DataIntegrityViolationException("Ya existe una categoría con el nombre: " + categoriaDTO.getNombre());
            }

            // Actualizar la categoría
            categoriaExistente.setNombre(categoriaDTO.getNombre());
            Categoria categoriaActualizada = categoriaServicio.guardarCategoria(categoriaExistente);
            logger.info("Categoría actualizada: " + categoriaActualizada);
            return ResponseEntity.ok(convertirADTO(categoriaActualizada));
        } catch (DataIntegrityViolationException e) {
            // Capturar excepción de violación de restricción única (categoría duplicada al actualizar)
            String mensajeError = "No se puede actualizar la categoría. El nombre ya está en uso por otra categoría.";
            return ResponseEntity.badRequest().body(mensajeError);
        }
    }

    @DeleteMapping("/categorias/eliminar/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarCategoria(@PathVariable Integer id) {
        Categoria categoria = categoriaServicio.buscarCategoriaPorId(id);
        if (categoria == null) {
            throw new RecursoNoEncontradoExcepcion("El ID de categoría no existe: " + id);
        }
        categoriaServicio.eliminarCategoria(categoria);
        logger.info("Categoría eliminada con éxito.");
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

    // Métodos de conversión entre DTO y entidad
    private CategoriaDTO convertirADTO(Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setCategoriaID(categoria.getCategoriaID());
        categoriaDTO.setNombre(categoria.getNombre());
        return categoriaDTO;
    }

    private Categoria convertirAEntidad(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setCategoriaID(categoriaDTO.getCategoriaID());
        categoria.setNombre(categoriaDTO.getNombre());
        return categoria;
    }

    // Método de validación para el campo nombre
    private boolean esNombreValido(String nombre) {
        return Pattern.matches(NOMBRE_REGEX, nombre);
    }
}

