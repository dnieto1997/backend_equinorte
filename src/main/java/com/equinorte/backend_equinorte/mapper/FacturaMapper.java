package com.equinorte.backend_equinorte.mapper;

import org.springframework.stereotype.Component;

import com.equinorte.backend_equinorte.dto.DetalleFacturaDto;
import com.equinorte.backend_equinorte.dto.FacturaDto;
import com.equinorte.backend_equinorte.entity.Factura;

@Component
public class FacturaMapper {


    public FacturaDto toDto(Factura factura) {

        FacturaDto dto = new FacturaDto();

        dto.setIdFactura(factura.getIdFactura());
        dto.setNumeroFactura(factura.getNumeroFactura());

        dto.setDetalles(
                factura.getDetalles()
                        .stream()
                        .map(d -> {
                            var det = new DetalleFacturaDto();
                            det.setIdDetalle(d.getIdDetalle());
                            det.setProducto(d.getProducto());
                            det.setCantidad(d.getCantidad());
                            det.setPrecioUnitario(d.getPrecioUnitario());
                            return det;
                        }).toList()
        );

        return dto;
    }
}