package gm.pci.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaDTO {
    private Integer CategoriaID;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres.")
    private String Nombre;

    // Getters y Setters
    public Integer getCategoriaID() {
        return CategoriaID;
    }

    public void setCategoriaID(Integer categoriaID) {
        CategoriaID = categoriaID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
