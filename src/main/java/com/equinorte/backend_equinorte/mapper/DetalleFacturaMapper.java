package com.equinorte.backend_equinorte.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.equinorte.backend_equinorte.dto.DetalleFacturaDto;
import com.equinorte.backend_equinorte.entity.DetalleFactura;

@Component
public class DetalleFacturaMapper {

    @Autowired
    private ModelMapper modelMapper;

    
    public DetalleFacturaDto toDto(DetalleFactura detalle) {
        return modelMapper.map(detalle, DetalleFacturaDto.class);
    }

  
    public DetalleFactura toEntity(DetalleFacturaDto dto) {
        return modelMapper.map(dto, DetalleFactura.class);
    }

    
    public void toEntity(DetalleFacturaDto dto, DetalleFactura detalleExistente) {
        modelMapper.map(dto, detalleExistente);
    }
}