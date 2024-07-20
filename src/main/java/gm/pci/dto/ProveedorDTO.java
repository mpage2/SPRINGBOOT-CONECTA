package gm.pci.dto;

public class ProveedorDTO {
    private Integer ProveedorID;
    private String Empresa;
    private String Ubicacion;
    private String Telefono;
    private String Email;
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
        Empresa = empresa;
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
