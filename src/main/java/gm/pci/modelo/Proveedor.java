package gm.pci.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProveedorID", unique = true, nullable = false, length = 11)
    private Integer ProveedorID;

//    @OneToMany(targetEntity = Proveedor.class, fetch = FetchType.LAZY, mappedBy = "proveedor", cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "ProveedorID", unique = true, nullable = false)
//    private List<Producto> productos;

    @Column(name = "Empresa", length = 50)
    private String Empresa;

    @Column(name = "Ubicacion", length = 50)
    private String Ubicacion;

    @Column(name = "Telefono", length = 9)
    private String Telefono;

    @Column(name = "Email", length = 50)
    private String Email;

    @Column(name = "RUC", length = 11)
    private String RUC;
}
//    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.PERSIST)
//    private List<Producto> productos;



