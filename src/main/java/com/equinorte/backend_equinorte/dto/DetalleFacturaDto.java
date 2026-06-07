package com.equinorte.backend_equinorte.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleFacturaDto {

   private Long idDetalle;

    private String producto;

    private Integer cantidad;

    private BigDecimal precioUnitario;
   
    private BigDecimal subtotal;
    
}