package gm.pci.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class MarcaDTO {

    private Integer MarcaID;

    @NotBlank(message = "El nombre de la marca es obligatorio")
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+$", message = "El nombre de la marca solo puede contener letras, espacios y tildes")
    private String Marca;

    public Integer getMarcaID() {
        return MarcaID;
    }

    public void setMarcaID(Integer marcaID) {
        MarcaID = marcaID;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }
}
