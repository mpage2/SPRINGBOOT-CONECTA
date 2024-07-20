package gm.pci.controlador;

import gm.pci.dto.RolDTO;
import gm.pci.modelo.Rol;
import gm.pci.servicio.IRolServicio;
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
public class RolControlador {

    private static final Logger logger = LoggerFactory.getLogger(RolControlador.class);

    @Autowired
    private IRolServicio rolServicio;

    @GetMapping("/roles")
    public List<RolDTO> obtenerRoles() {
        List<Rol> roles = rolServicio.listarRoles();
        logger.info("Roles obtenidos: " + roles.size());

        // Convertir lista de Rol a lista de RolDTO
        List<RolDTO> rolesDTO = roles.stream()
                .map(this::convertirARolDTO)
                .collect(Collectors.toList());

        return rolesDTO;
    }

    // Método para convertir de Rol a RolDTO
    private RolDTO convertirARolDTO(Rol rol) {
        return new RolDTO(
                rol.getRolID(),
                rol.getRol()
        );
    }
}
