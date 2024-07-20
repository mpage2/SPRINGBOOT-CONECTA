package gm.pci.controlador;

import gm.pci.dto.ProductoDTO;
import gm.pci.excepcion.RecursoNoEncontradoExcepcion;
import gm.pci.modelo.Producto;
import gm.pci.servicio.IProductoServicio;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pci-app")
@CrossOrigin
public class ProductoControlador {

    private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class);

    @Autowired
    private IProductoServicio productoServicio;

    @GetMapping("/productos")
    public List<Producto> obtenerProductos() {
        List<Producto> productos = productoServicio.listarProductos();
        productos.forEach((producto -> logger.info(producto.toString())));
        return productos;
    }

    @PostMapping("/productos/guardar")
    public ResponseEntity<?> agregarProducto(@Valid @RequestBody ProductoDTO productoDTO, BindingResult result) {
        // Validar campos antes de guardar
        String mensajeError = validarProducto(productoDTO);
        if (mensajeError != null) {
            return ResponseEntity.badRequest().body(mensajeError);
        }

        Producto producto = convertirADominio(productoDTO);
        logger.info("Producto a agregar " + producto);
        Producto productoGuardado = productoServicio.guardarProducto(producto);
        return ResponseEntity.ok(productoGuardado);
    }

    @PutMapping("/productos/actualizar/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Integer id, @Valid @RequestBody ProductoDTO productoDTO, BindingResult result) {
        // Validar campos antes de actualizar
        String mensajeError = validarProducto(productoDTO);
        if (mensajeError != null) {
            return ResponseEntity.badRequest().body(mensajeError);
        }

        Producto productoExistente = productoServicio.buscarProductoPorId(id);
        if (productoExistente == null) {
            throw new RecursoNoEncontradoExcepcion("El ID de producto no existe: " + id);
        }

        productoExistente.setNombre(productoDTO.getNombre());
        productoExistente.setMarcaid(productoDTO.getMarcaid());
        productoExistente.setDescripcion(productoDTO.getDescripcion());
        productoExistente.setPrecio(productoDTO.getPrecio());
        productoExistente.setStock(productoDTO.getStock());
        productoExistente.setCategoriaid(productoDTO.getCategoriaid());
        productoExistente.setProveedorID(productoDTO.getProveedorID());
        productoExistente.setUsuarioID(productoDTO.getUsuarioID());

        Producto productoActualizado = productoServicio.guardarProducto(productoExistente);
        logger.info("Producto actualizado: " + productoActualizado);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/productos/eliminar/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable Integer id) {
        Producto producto = productoServicio.buscarProductoPorId(id);
        if (producto == null) {
            throw new RecursoNoEncontradoExcepcion("El ID de producto no existe: " + id);
        }
        productoServicio.eliminarProducto(producto);
        logger.info("Producto eliminado con éxito.");
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

    private Producto convertirADominio(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setProductoID(productoDTO.getProductoID());
        producto.setNombre(productoDTO.getNombre());
        producto.setMarcaid(productoDTO.getMarcaid());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setCategoriaid(productoDTO.getCategoriaid());
        producto.setProveedorID(productoDTO.getProveedorID());
        producto.setUsuarioID(productoDTO.getUsuarioID());
        return producto;
    }

    // Método para validar campos del producto y devolver mensajes de error
    private String validarProducto(ProductoDTO productoDTO) {


        if (!validarStock(productoDTO.getStock())) {
            return "El stock debe ser un número válido (solo enteros).";
        }

        return null; // Retorna null si todas las validaciones pasan
    }



    private boolean validarStock(Integer stock) {
        // Solo números enteros
        return stock != null;
    }
}
