package gm.pci.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductoID", unique = true, nullable = false, length = 11)
    private Integer ProductoID;

    @Column(name = "Nombre", length = 50)
    private String Nombre;

    @Column(name = "marcaid", length = 11)
    private Integer marcaid;

    @Column(name = "Descripcion", length = 50)
    private String Descripcion;

    @Column(name = "Precio", precision = 10, scale = 2)
    private BigDecimal Precio;

    @Column(name = "Stock", length = 11)
    private Integer Stock;

    @Column(name = "categoriaid", length = 11)
    private Integer categoriaid;


    @Column(name = "ProveedorID", length = 11)
    private Integer ProveedorID;

    @Column(name = "UsuarioID", length = 11)
    private Integer UsuarioID;
}