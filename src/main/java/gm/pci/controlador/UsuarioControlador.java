package gm.pci.controlador;

import gm.pci.dto.UsuarioDTO;
import gm.pci.excepcion.RecursoNoEncontradoExcepcion;
import gm.pci.modelo.Usuario;
import gm.pci.servicio.IUsuarioServicio;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pci-app")
@CrossOrigin
public class UsuarioControlador {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioControlador.class);

    @Autowired
    private IUsuarioServicio usuarioServicio;

    @GetMapping("/usuarios")
    public List<Usuario> obtenerUsuarios() {
        return usuarioServicio.listarUsuarios();
    }

    @PostMapping("/usuarios/guardar")
    public ResponseEntity<?> agregarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        // Validar campos antes de guardar
        String mensajeError = validarUsuario(usuarioDTO);
        if (mensajeError != null) {
            return ResponseEntity.badRequest().body(mensajeError);
        }

        Usuario usuario = convertirADominio(usuarioDTO);
        try {
            Usuario usuarioGuardado = usuarioServicio.guardarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Datos duplicados. No se pudo guardar el usuario.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo guardar el usuario.");
        }
    }

    @PutMapping("/usuarios/actualizar/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        // Validar campos antes de actualizar
        String mensajeError = validarUsuario(usuarioDTO);
        if (mensajeError != null) {
            return ResponseEntity.badRequest().body(mensajeError);
        }

        Usuario usuario = usuarioServicio.buscarUsuarioPorId(id);
        if (usuario == null) {
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        }

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setDNI(usuarioDTO.getDNI());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setUsuario(usuarioDTO.getUsuario());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setRolid(usuarioDTO.getRolid());

        try {
            usuarioServicio.guardarUsuario(usuario);
            return ResponseEntity.ok(usuario);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Datos duplicados. No se pudo actualizar el usuario.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo actualizar el usuario.");
        }
    }

    @DeleteMapping("/usuarios/eliminar/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarUsuario(@PathVariable Integer id) {
        Usuario usuario = usuarioServicio.buscarUsuarioPorId(id);
        if (usuario == null) {
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        }
        usuarioServicio.eliminarUsuario(usuario);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> userMap) {
        String username = userMap.get("usuario");
        String password = userMap.get("password");
        Usuario usuario = usuarioServicio.buscarUsuarioPorNombreUsuario(username);
        if (usuario == null || !usuario.getPassword().equals(password)) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
        usuario.generarToken();
        usuarioServicio.guardarUsuario(usuario); // Guarda el usuario actualizado con el nuevo token
        return ResponseEntity.ok(usuario);
    }

    private Usuario convertirADominio(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsuarioID(usuarioDTO.getUsuarioID());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setDNI(usuarioDTO.getDNI());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setUsuario(usuarioDTO.getUsuario());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setRolid(usuarioDTO.getRolid());
        return usuario;
    }

    // Método para validar campos del usuario y devolver mensajes de error
    private String validarUsuario(UsuarioDTO usuario) {
        if (!validarNombre(usuario.getNombre())) {
            return "El nombre solo puede contener letras, la letra 'ñ', espacios y tildes.";
        }

        if (!validarDNI(usuario.getDNI())) {
            return "El DNI debe tener exactamente 8 dígitos y solo puede contener números.";
        }

        if (!validarTelefono(usuario.getTelefono())) {
            return "El teléfono debe tener exactamente 9 dígitos, solo contener números y comenzar con el número 9.";
        }

        if (!validarEmail(usuario.getEmail())) {
            return "El email debe contener un '@'.";
        }

        return null; // Retorna null si todas las validaciones pasan
    }

    private boolean validarNombre(String nombre) {
        // Solo letras, la letra 'ñ', espacios y tildes
        return nombre.matches("^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+$");
    }

    private boolean validarDNI(String dni) {
        // Solo números, exactamente 8 dígitos
        return dni.matches("^\\d{8}$");
    }

    private boolean validarTelefono(String telefono) {
        // Solo números, debe empezar con 9 y tener 9 dígitos
        return telefono.matches("^9\\d{8}$");
    }

    private boolean validarEmail(String email) {
        // Debe contener un '@'
        return email.contains("@");
    }
}
