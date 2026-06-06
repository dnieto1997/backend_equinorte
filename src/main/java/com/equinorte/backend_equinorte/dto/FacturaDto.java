package com.equinorte.backend_equinorte.dto;


import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDto {

    private Long idFactura;

    private String numeroFactura;

    private List<DetalleFacturaDto> detalles;
}