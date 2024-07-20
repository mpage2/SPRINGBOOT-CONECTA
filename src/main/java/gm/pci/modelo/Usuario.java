package gm.pci.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "usuario")

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UsuarioID", unique = true, nullable = false, length = 11)
    private Integer UsuarioID;

    @Column(name = "Nombre", length = 50)
    private String Nombre;

    @Column(name = "DNI", length = 8)
    private String DNI;

    @Column(name = "Telefono", length = 9)
    private String Telefono;

    @Column(name = "Email", length = 50)
    private String Email;

    @Column(name = "usuario", length = 50)
    private String usuario;

    @Column(name = "Password", length = 50)
    private String Password;

    @Column(name = "rolid", length = 11)
    private Integer rolid;

    @Column(name = "Token", length = 16) // Agrega un campo para el token
    private String Token;
// Método para generar el token automáticamente
public void generarToken() {
    this.Token = UUID.randomUUID().toString().substring(0, 16);
     }
}


