package gm.pci.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalidaDTO {
    private Integer salidaID;
    private Integer productoID;
    private Integer marcaid;
    private Integer cantidad;
    private BigDecimal precio;
    private Integer categoriaid;
    private Integer proveedorID;
    private Integer usuarioID;
}
