package gm.pci.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ProveedorDTO {

    private Integer ProveedorID;

    @NotBlank(message = "El campo Empresa no puede estar vacío.")
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+$", message = "El nombre de la empresa solo puede contener letras, espacios y la letra ñ con o sin tilde.")
    private String Empresa;

    private String Ubicacion;

    @NotBlank(message = "El campo Telefono no puede estar vacío.")
    @Pattern(regexp = "^[0-9]{9}$", message = "El número de teléfono debe tener 9 dígitos y solo puede contener números.")
    private String Telefono;

    @NotBlank(message = "El campo Email no puede estar vacío.")
    @Email(message = "El correo electrónico debe tener un formato válido y contener un '@'.")
    private String Email;

    @NotBlank(message = "El campo RUC no puede estar vacío.")
    @Pattern(regexp = "^(10|20)\\d{9}$", message = "El RUC debe comenzar con 10 o 20 y tener 11 dígitos.")
    private String RUC;

    // Getters y Setters
    public Integer getProveedorID() {
        return ProveedorID;
    }

    public void setProveedorID(Integer proveedorID) {
        ProveedorID = proveedorID;
    }

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String empresa) {
        Empresa = empresa != null ? empresa.toUpperCase() : null;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRUC() {
        return RUC;
    }

    public void setRUC(String ruc) {
        RUC = ruc;
    }
}