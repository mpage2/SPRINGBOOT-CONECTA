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
@Table(name = "salida")
public class Salida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SalidaID", unique = true, nullable = false, length = 11)
    private Integer SalidaID;


    @Column(name = "ProductoID", length = 11)
    private Integer ProductoID;

    @Column(name = "marcaid", length = 11)
    private Integer marcaid;

    @Column(name = "Cantidad", length = 11)
    private Integer Cantidad;

    @Column(name = "Precio", precision = 10, scale = 2)
    private BigDecimal Precio;

    @Column(name = "categoriaid", length = 11)
    private Integer categoriaid;

    @Column(name = "ProveedorID", length = 11)
    private Integer ProveedorID;

    @Column(name = "UsuarioID", length = 11)
    private Integer UsuarioID;

}
