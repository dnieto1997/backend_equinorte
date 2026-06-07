package com.equinorte.backend_equinorte.dto;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDto {

    private Long idFactura;

    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
     private LocalDateTime fechaCreacion;

    private String numeroFactura;

    private List<DetalleFacturaDto> detalles;
}