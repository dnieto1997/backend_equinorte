package com.equinorte.backend_equinorte.dto;

import com.equinorte.backend_equinorte.entity.TipoUsuario;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecalculoFacturaDto {

   
    private BigDecimal nuevoSubtotal;

    
    private TipoUsuario tipoUsuario;
}