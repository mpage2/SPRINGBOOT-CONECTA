package gm.pci.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProductoDTO {
    private Integer productoID;

    @NotNull(message = "El nombre no puede estar vacío.")
    private String nombre;

    @NotNull(message = "La marca es requerida.")
    private Integer marcaid;

    private String descripcion;

    @NotNull(message = "El precio no puede estar vacío.")
    private BigDecimal precio;

    @NotNull(message = "El stock no puede estar vacío.")
    @Min(value = 0, message = "El stock debe ser un número positivo.")
    private Integer stock;

    @NotNull(message = "La categoría es requerida.")
    private Integer categoriaid;

    @NotNull(message = "El proveedor es requerido.")
    private Integer proveedorID;

    private Integer usuarioID;

    // Getters y setters

    public Integer getProductoID() {
        return productoID;
    }

    public void setProductoID(Integer productoID) {
        this.productoID = productoID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getMarcaid() {
        return marcaid;
    }

    public void setMarcaid(Integer marcaid) {
        this.marcaid = marcaid;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCategoriaid() {
        return categoriaid;
    }

    public void setCategoriaid(Integer categoriaid) {
        this.categoriaid = categoriaid;
    }

    public Integer getProveedorID() {
        return proveedorID;
    }

    public void setProveedorID(Integer proveedorID) {
        this.proveedorID = proveedorID;
    }

    public Integer getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(Integer usuarioID) {
        this.usuarioID = usuarioID;
    }
}